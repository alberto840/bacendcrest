package com.project.pet_veteriana.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.project.pet_veteriana.bl.*;
import com.project.pet_veteriana.dto.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/ai")
public class IAController {
    private static final Logger logger = LoggerFactory.getLogger(IAController.class);
    private static final String NO_PETS_MSG = "No hay mascotas registradas";
    private static final String NO_OFFERS_MSG = "No hay ofertas disponibles";
    private static final String USER_NOT_FOUND_MSG = "Usuario no encontrado";

    private final GeminiBl geminiService;
    private final PetsBl petsBl;
    private final OffersProductsBl offersProductsBl;
    private final ProductsBl productsBl;
    private final OffersBl offersBl;
    private final WhatsAppService whatsAppService;
    private final UsersBl usersBl;

    public IAController(GeminiBl geminiService, PetsBl petsBl,
                       OffersProductsBl offersProductsBl, ProductsBl productsBl,
                       OffersBl offersBl, WhatsAppService whatsAppService, UsersBl usersBl) {
        this.geminiService = geminiService;
        this.petsBl = petsBl;
        this.offersProductsBl = offersProductsBl;
        this.productsBl = productsBl;
        this.offersBl = offersBl;
        this.whatsAppService = whatsAppService;
        this.usersBl = usersBl;
    }

    @GetMapping("/personalized-recommendations")
    public ResponseEntity<Map<String, Object>> getPersonalizedRecommendations(
            @RequestParam Integer userId) {

        try {
            return processRecommendations(userId);
        } catch (Exception e) {
            logger.error("Error en recomendaciones personalizadas", e);
            return buildErrorResponse(e);
        }
    }
    
    @PostMapping("/send-ia-recommendations-whatsapp")
    public ResponseEntity<Map<String, Object>> sendIARecommendationsToWhatsApp(@RequestParam Integer userId) {
        try {
            Optional<UsersDto> userOptional = usersBl.getUserById(userId);
            if (userOptional.isEmpty()) {
                return buildResponse(USER_NOT_FOUND_MSG);
            }
            
            UsersDto user = userOptional.get();
            String phoneNumber = formatPhoneNumber(user.getPhoneNumber());

            // 1. Generar las recomendaciones con IA
            ResponseEntity<Map<String, Object>> iaResponse = processRecommendations(userId);
            
            if (!iaResponse.getStatusCode().is2xxSuccessful()) {
                return iaResponse;
            }

            // 2. Enviar por WhatsApp exactamente lo que generó la IA
            whatsAppService.sendIARecommendations(phoneNumber, user.getName(), iaResponse.getBody());

            return ResponseEntity.ok(Map.of(
                "status", "success",
                "message", "Recomendaciones de IA enviadas a " + user.getName(),
                "timestamp", LocalDateTime.now()
            ));

        } catch (Exception e) {
            logger.error("Error enviando recomendaciones de IA por WhatsApp", e);
            return buildErrorResponse(e);
        }
    }

    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return null;
        
        String cleaned = phoneNumber.replaceAll("[^0-9]", "");
        
        if (!cleaned.startsWith("591") && cleaned.length() == 8) {
            return "591" + cleaned;
        }
        
        return cleaned;
    }

    private ResponseEntity<Map<String, Object>> processRecommendations(Integer userId) {
        List<PetsDto> userPets = petsBl.getPetsByUserId(userId);
        if (userPets.isEmpty()) return buildResponse(NO_PETS_MSG);

        List<OffersProductsDto> activeOffers = getFilteredOffers();
        if (activeOffers.isEmpty()) return buildResponse(NO_OFFERS_MSG);

        Map<String, Object> promptData = preparePromptData(userPets, activeOffers);
        Map<String, Object> geminiResponse = executeGeminiQuery(promptData);

        return buildSuccessResponse(geminiResponse, userId, userPets);
    }

    private List<OffersProductsDto> getFilteredOffers() {
        return offersProductsBl.getAllOffersProducts().stream()
                .filter(op -> {
                    try {
                        OffersDto offer = offersBl.getOfferById(op.getOfferId());
                        return offer != null && isOfferActive(offer);
                    } catch (Exception e) {
                        logger.error("Error procesando oferta", e);
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    private boolean isOfferActive(OffersDto offer) {
        LocalDateTime now = LocalDateTime.now();
        return now.isAfter(offer.getStartDate()) && now.isBefore(offer.getEndDate());
    }

    private Map<String, Object> preparePromptData(List<PetsDto> pets, List<OffersProductsDto> offers) {
        List<Map<String, Object>> productsData = offers.stream()
                .map(this::buildProductData)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        return Map.of(
                "pets", pets,
                "products", productsData,
                "currentDate", LocalDateTime.now()
        );
    }

    private Map<String, Object> buildProductData(OffersProductsDto op) {
        OffersDto offer = offersBl.getOfferById(op.getOfferId());
        if (offer == null) {
            logger.warn("Oferta no encontrada para ID: {}", op.getOfferId());
            return null;
        }

        return productsBl.getProductById(op.getProductId())
                .map(product -> Map.of(
                        "product", product,
                        "offer", offer,
                        "discountedPrice", calculateDiscountedPrice(product.getPrice(), offer)
                ))
                .orElseGet(() -> {
                    logger.warn("Producto no encontrado para ID: {}", op.getProductId());
                    return null;
                });
    }

    private Map<String, Object> executeGeminiQuery(Map<String, Object> promptData) {
        String prompt = buildCompletePrompt(promptData);
        logger.debug("Enviando prompt a Gemini: {}", prompt);

        try {
            Object response = geminiService.generateContent(prompt);
            return parseGeminiResponse(response.toString());
        } catch (Exception e) {
            logger.error("Error al consultar Gemini", e);
            throw new RuntimeException("Error en servicio de IA: " + e.getMessage());
        }
    }

    private String buildCompletePrompt(Map<String, Object> data) {
        List<PetsDto> pets = (List<PetsDto>) data.get("pets");
        List<Map<String, Object>> products = (List<Map<String, Object>>) data.get("products");

        return String.format("""
                [INSTRUCCIONES ESTRICTAS]
                Devuelve EXCLUSIVAMENTE JSON con formato:
                {
                "recommendedProductIds": [números],
                "explanations": {
                "general": "texto",
                "byPet": {"nombre_mascota": "texto"}
                }
                }
                
                **Mascotas**:
                %s
                
                **Productos con ofertas**:
                %s
                """, formatPets(pets), formatProducts(products));
    }

    private String formatPets(List<PetsDto> pets) {
        return pets.stream()
                .map(p -> String.format(
                        "- %s: %s, %s, %s años, %s kg, alergias: %s, notas: %s",
                        p.getPetName(), p.getSpecies(), p.getPetBreed(), p.getPetAge(), p.getWeight(),
                        Optional.ofNullable(p.getAllergies()).orElse("ninguna"),
                        Optional.ofNullable(p.getBehaviorNotes()).orElse("ninguna")
                ))
                .collect(Collectors.joining("\n"));
    }

    private String formatProducts(List<Map<String, Object>> products) {
        return products.stream()
                .map(p -> {
                    ProductsDto prod = (ProductsDto) p.get("product");
                    OffersDto off = (OffersDto) p.get("offer");
                    return String.format(
                            "- ID: %d | %s: %s | Precio: %.2f | Descuento: %.2f%s",
                            prod.getProductId(), prod.getName(), prod.getDescription(), prod.getPrice(),
                            off.getDiscountValue(), off.getDiscountType().equals("PERCENTAGE") ? "%%" : "USD"
                    );
                })
                .collect(Collectors.joining("\n"));
    }

    private Map<String, Object> parseGeminiResponse(String response) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(extractJson(response), new TypeReference<>() {});
        } catch (Exception e) {
            logger.error("Error parseando respuesta Gemini: {}", response, e);
            throw new RuntimeException("Error procesando respuesta IA: " + e.getMessage());
        }
    }

    private String extractJson(String response) {
        if (response.trim().startsWith("{")) return response;
        if (response.contains("```json")) return response.substring(response.indexOf("{"), response.lastIndexOf("}") + 1);
        if (response.startsWith("```")) return response.substring(3, response.length() - 3).trim();

        int start = response.indexOf("{");
        int end = response.lastIndexOf("}");
        return start >= 0 && end > start ? response.substring(start, end + 1) : response;
    }

    private ResponseEntity<Map<String, Object>> buildSuccessResponse(
            Map<String, Object> geminiResponse, int userId, List<PetsDto> userPets) {

        List<Integer> productIds = (List<Integer>) geminiResponse.get("recommendedProductIds");
        Map<String, Object> explanations = (Map<String, Object>) geminiResponse.get("explanations");

        List<Map<String, Object>> petsWithProducts = userPets.stream()
                .map(pet -> Map.of(
                        "petName", pet.getPetName(),
                        "species", pet.getSpecies(),
                        "recommendedProducts", getPetProducts(productIds, pet, explanations)
                ))
                .collect(Collectors.toList());

        return ResponseEntity.ok().body(Map.of(
                "userId", userId,
                "pets", petsWithProducts,
                "timestamp", LocalDateTime.now(),
                "status", "success"
        ));
    }

    private List<Map<String, Object>> getPetProducts(
            List<Integer> productIds, PetsDto pet, Map<String, Object> explanations) {

        return productIds.stream()
                .map(productsBl::getProductById)
                .flatMap(Optional::stream)
                .filter(product -> isRecommendedForPet(product, pet, explanations))
                .map(product -> {
                    OffersDto offer = findOfferForProduct(product.getProductId());
                    return buildProductInfo(product, offer);
                })
                .collect(Collectors.toList());
    }

    private boolean isRecommendedForPet(ProductsDto product, PetsDto pet, Map<String, Object> explanations) {
        Map<String, String> byPet = (Map<String, String>) explanations.get("byPet");
        return byPet != null && byPet.containsKey(pet.getPetName());
    }

    private OffersDto findOfferForProduct(Integer productId) {
        return offersProductsBl.getAllOffersProducts().stream()
                .filter(op -> productId.equals(op.getProductId()))
                .map(op -> offersBl.getOfferById(op.getOfferId()))
                .filter(offer -> offer != null && isOfferActive(offer))
                .findFirst()
                .orElse(null);
    }

    private Map<String, Object> buildProductInfo(ProductsDto product, OffersDto offer) {
        Map<String, Object> info = new HashMap<>();
        info.put("productId", product.getProductId());
        info.put("productName", product.getName());
        info.put("originalPrice", product.getPrice());
        info.put("discountedPrice", offer != null ?
                calculateDiscountedPrice(product.getPrice(), offer) : product.getPrice());

        if (offer != null) {
            info.put("offerName", offer.getName());
            if ("PERCENTAGE".equals(offer.getDiscountType())) {
                info.put("discountPercentage", offer.getDiscountValue());
            }
        }
        return info;
    }

    private double calculateDiscountedPrice(double price, OffersDto offer) {
        return "PERCENTAGE".equals(offer.getDiscountType()) ?
                price * (1 - offer.getDiscountValue() / 100) :
                price - offer.getDiscountValue();
    }

    private ResponseEntity<Map<String, Object>> buildResponse(String message) {
        return ResponseEntity.ok().body(Map.of(
                "message", message,
                "timestamp", LocalDateTime.now(),
                "status", "success"
        ));
    }

    private ResponseEntity<Map<String, Object>> buildErrorResponse(Exception e) {
        int statusCode = 500;
        String errorMessage = "Error en el servicio";
        
        if (e.getMessage() != null) {
            if (e.getMessage().contains("authentication")) {
                statusCode = 401;
                errorMessage = "Error de autenticación con WhatsApp";
            } else if (e.getMessage().contains("teléfono")) {
                statusCode = 400;
                errorMessage = "Número de teléfono inválido";
            }
        }
        
        return ResponseEntity.status(statusCode).body(Map.of(
                "error", errorMessage,
                "reason", e.getMessage() != null ? e.getMessage() : "Error desconocido",
                "timestamp", LocalDateTime.now(),
                "status", "error"
        ));
    }
}