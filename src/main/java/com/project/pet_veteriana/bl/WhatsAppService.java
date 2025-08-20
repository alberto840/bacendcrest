package com.project.pet_veteriana.bl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.*;

@Service
public class WhatsAppService {
    private static final Logger logger = LoggerFactory.getLogger(WhatsAppService.class);

    @Value("${whatsapp.api.token}")
    private String apiToken;

    @Value("${whatsapp.api.phone-number-id}")
    private String phoneNumberId;

    @Value("${whatsapp.api.url}")
    private String apiUrl;

    private final HttpClient httpClient;
    private final ObjectMapper objectMapper;

    public WhatsAppService() {
        this.httpClient = HttpClient.newBuilder()
                .connectTimeout(Duration.ofSeconds(10))
                .build();
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Envía recomendaciones generadas por IA a través de WhatsApp
     */
    public void sendIARecommendations(String phoneNumber, String userName, Map<String, Object> iaData) {
        try {
            Objects.requireNonNull(phoneNumber, "El número de teléfono no puede ser nulo");
            Objects.requireNonNull(userName, "El nombre de usuario no puede ser nulo");
            
            if (iaData == null) {
                throw new IllegalArgumentException("Los datos de IA no pueden ser nulos");
            }

            String formattedNumber = formatPhoneNumber(phoneNumber);
            if (!isValidWhatsAppNumber(formattedNumber)) {
                throw new IllegalArgumentException("Número de WhatsApp inválido: " + phoneNumber);
            }

            String message = formatIARecommendations(userName, iaData);
            sendWhatsAppMessage(formattedNumber, message);
            
        } catch (Exception e) {
            logger.error("Error enviando recomendaciones de IA a WhatsApp", e);
            throw new WhatsAppException("Error al enviar recomendaciones por WhatsApp", e);
        }
    }

    /**
     * Formatea las recomendaciones para WhatsApp
     */
    /**
     * Formatea las recomendaciones para WhatsApp
     */
    private String formatIARecommendations(String userName, Map<String, Object> iaData) {
        try {
            StringBuilder message = new StringBuilder();
            message.append("¡Hola ").append(userName).append("! 🐾\n\n");
            message.append("*Tus recomendaciones personalizadas:*\n\n");

            // 1. Manejo seguro de las explicaciones
            Map<String, Object> explanations = getSafeMap(iaData, "explanations");
            
            // Explicación general
            String generalMsg = getSafeString(explanations, "general", 
                "Basado en el perfil de tus mascotas, te recomendamos:");
            message.append(generalMsg).append("\n\n");

            // 2. Procesar mascotas
            List<Map<String, Object>> pets = getSafeList(iaData, "pets");
            if (pets.isEmpty()) {
                message.append("No hay recomendaciones específicas disponibles.\n");
            } else {
                Map<String, Object> byPetExplanationsObj = getSafeMap(explanations, "byPet");
                Map<String, String> byPetExplanations = new HashMap<>();
                if (byPetExplanationsObj != null) {
                    for (Map.Entry<String, Object> entry : byPetExplanationsObj.entrySet()) {
                        if (entry.getValue() != null) {
                            byPetExplanations.put(entry.getKey(), entry.getValue().toString());
                        }
                    }
                }
                
                for (Map<String, Object> pet : pets) {
                    processPetRecommendation(message, pet, byPetExplanations);
                }
            }

            // Obtener el userId del objeto iaData
            Integer userId = (Integer) iaData.get("userId");
            if (userId != null) {
                message.append("Para ver más detalles, visita: http://localhost:4200/web-pet/#/producto/").append(userId);
            } else {
                message.append("\n¡Visítanos para más detalles!");
            }
            
            return message.toString();

        } catch (Exception e) {
            logger.error("Error formateando mensaje", e);
            return "¡Hola " + userName + "! Tienes nuevas recomendaciones. Visita la app para ver los detalles.";
        }
    }

    /**
     * Procesa los datos de cada mascota
     */
    private void processPetRecommendation(StringBuilder message, 
                                        Map<String, Object> petData,
                                        Map<String, String> byPetExplanations) {
        
        String petName = getSafeString(petData, "petName", "Tu mascota");
        String species = getSafeString(petData, "species", "");
        
        message.append("🐕 *").append(petName).append("*");
        if (!species.isEmpty()) {
            message.append(" (").append(species).append(")");
        }
        message.append(":\n");
        
        // Explicación específica
        if (byPetExplanations != null && byPetExplanations.containsKey(petName)) {
            message.append(byPetExplanations.get(petName)).append("\n");
        }
        
        // Productos recomendados
        List<Map<String, Object>> products = getSafeList(petData, "recommendedProducts");
        if (products.isEmpty()) {
            message.append("  - No hay productos recomendados actualmente\n");
        } else {
            for (Map<String, Object> product : products) {
                processProduct(message, product);
            }
        }
        message.append("\n");
    }

    /**
     * Procesa cada producto recomendado
     */
    private void processProduct(StringBuilder message, Map<String, Object> product) {
        String name = getSafeString(product, "productName", "Producto recomendado");
        double price = getSafeDouble(product, "discountedPrice", 0.0);
        
        message.append("  - ").append(name).append(": $").append(String.format("%.2f", price));
        
        if (product.containsKey("offerName")) {
            message.append(" (¡Oferta! ").append(product.get("offerName")).append(")");
        }
        message.append("\n");
    }

    /**
     * Envía el mensaje a la API de WhatsApp
     */
    private void sendWhatsAppMessage(String to, String body) {
        try {
            Objects.requireNonNull(apiToken, "Token de WhatsApp no configurado");
            
            Map<String, Object> requestBody = Map.of(
                "messaging_product", "whatsapp",
                "to", to,
                "type", "text",
                "text", Map.of("body", body)
            );

            HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(String.format("%s/%s/messages", apiUrl, phoneNumberId)))
                .header("Authorization", "Bearer " + apiToken.trim())
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(objectMapper.writeValueAsString(requestBody)))
                .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() >= 400) {
                logger.error("Error en API WhatsApp - Status: {} - Response: {}", 
                    response.statusCode(), response.body());
                throw new WhatsAppException("API WhatsApp respondió con error: " + response.body());
            }

            logger.info("Mensaje enviado exitosamente a {}", to);
            
        } catch (Exception e) {
            logger.error("Error enviando mensaje WhatsApp", e);
            throw new WhatsAppException("Error al comunicarse con API WhatsApp", e);
        }
    }

    /**
     * Formatea el número de teléfono
     */
    private String formatPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) return null;
        String cleaned = phoneNumber.replaceAll("[^0-9]", "");
        
        if (!cleaned.startsWith("591") && cleaned.length() == 8) {
            return "591" + cleaned;
        }
        return cleaned;
    }

    /**
     * Valida el formato del número
     */
    private boolean isValidWhatsAppNumber(String phoneNumber) {
        return phoneNumber != null && phoneNumber.matches("^\\d{10,15}$");
    }

    // Métodos auxiliares para manejo seguro de datos
    private Map<String, Object> getSafeMap(Map<String, Object> source, String key) {
        try {
            if (source != null && source.containsKey(key) && source.get(key) instanceof Map) {
                return (Map<String, Object>) source.get(key);
            }
        } catch (Exception e) {
            logger.warn("Error obteniendo mapa para key: {}", key, e);
        }
        return new HashMap<>();
    }

    private List<Map<String, Object>> getSafeList(Map<String, Object> source, String key) {
        try {
            if (source != null && source.containsKey(key) && source.get(key) instanceof List) {
                return (List<Map<String, Object>>) source.get(key);
            }
        } catch (Exception e) {
            logger.warn("Error obteniendo lista para key: {}", key, e);
        }
        return new ArrayList<>();
    }

    private String getSafeString(Map<String, Object> source, String key, String defaultValue) {
        try {
            if (source != null && source.containsKey(key) && source.get(key) != null) {
                return source.get(key).toString();
            }
        } catch (Exception e) {
            logger.warn("Error obteniendo string para key: {}", key, e);
        }
        return defaultValue;
    }

    private double getSafeDouble(Map<String, Object> source, String key, double defaultValue) {
        try {
            if (source != null && source.containsKey(key)) {
                Object value = source.get(key);
                if (value instanceof Number) {
                    return ((Number) value).doubleValue();
                }
                return Double.parseDouble(value.toString());
            }
        } catch (Exception e) {
            logger.warn("Error obteniendo double para key: {}", key, e);
        }
        return defaultValue;
    }

    // Excepción personalizada
    public static class WhatsAppException extends RuntimeException {
        public WhatsAppException(String message) {
            super(message);
        }
        
        public WhatsAppException(String message, Throwable cause) {
            super(message, cause);
        }
    }
}