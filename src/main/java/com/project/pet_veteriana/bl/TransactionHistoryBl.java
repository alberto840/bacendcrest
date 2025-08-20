package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.TransactionHistoryDto;
import com.project.pet_veteriana.dto.UsersDto;
import com.project.pet_veteriana.dto.ServicesDto; // Importa ServicesDto
import com.project.pet_veteriana.dto.ProductsDto; // Importa ProductsDto
import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.entity.TransactionHistory;
import com.project.pet_veteriana.entity.Users;
import com.project.pet_veteriana.entity.Providers;
import com.project.pet_veteriana.entity.Services;
import com.project.pet_veteriana.entity.Products;

import com.project.pet_veteriana.repository.TransactionHistoryRepository;
import com.project.pet_veteriana.repository.UsersRepository;
import com.project.pet_veteriana.repository.ProvidersRepository;
import com.project.pet_veteriana.repository.ServicesRepository;
import com.project.pet_veteriana.repository.ProductsRepository; // Importa ProductsRepository

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionHistoryBl {

    @Autowired
    private TransactionHistoryRepository transactionHistoryRepository;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private ServicesRepository servicesRepository;

    @Autowired
    private ProductsRepository productsRepository; // Inyecta ProductsRepository

    @Transactional
    public TransactionHistoryDto createTransactionHistory(TransactionHistoryDto dto) {
        if (dto.getUserId() == null || dto.getUser() == null) {
            throw new IllegalArgumentException("User ID is required to create a transaction.");
        }

        Users user = usersRepository.findById(dto.getUserId())
                .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getUserId()));

        Services service = null;
        if (dto.getServiceId() != null) {
            service = servicesRepository.findById(dto.getServiceId())
                    .orElseThrow(() -> new IllegalArgumentException("Service not found with ID: " + dto.getServiceId()));
        }

        Products product = null;
        if (dto.getProductId() != null) {
            product = productsRepository.findById(dto.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + dto.getProductId()));
        }

        TransactionHistory transactionHistory = new TransactionHistory();
        transactionHistory.setTotalAmount(dto.getTotalAmount());
        transactionHistory.setStatus(dto.getStatus());
        transactionHistory.setCreatedAt(LocalDateTime.now());
        transactionHistory.setUser(user);
        transactionHistory.setService(service);
        transactionHistory.setProduct(product);
        transactionHistory.setQuantity(dto.getQuantity());
        transactionHistory.setAmountPerUnit(dto.getAmountPerUnit());
        transactionHistory.setDetail(dto.getDetail());

        TransactionHistory savedTransaction = transactionHistoryRepository.save(transactionHistory);
        return convertToDto(savedTransaction);
    }

    public List<TransactionHistoryDto> getAllTransactionHistories() {
        List<TransactionHistory> transactions = transactionHistoryRepository.findAll();
        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public TransactionHistoryDto getTransactionHistoryById(Integer id) {
        Optional<TransactionHistory> transactionOptional = transactionHistoryRepository.findById(id);
        if (transactionOptional.isPresent()) {
            return convertToDto(transactionOptional.get());
        }
        return null;
    }

    @Transactional
    public TransactionHistoryDto updateTransactionHistory(Integer id, TransactionHistoryDto dto) {
        Optional<TransactionHistory> transactionOptional = transactionHistoryRepository.findById(id);
        if (transactionOptional.isPresent()) {
            TransactionHistory transactionHistory = transactionOptional.get();

            if (dto.getUserId() != null ) {
                Users newUser = usersRepository.findById(dto.getUserId())
                        .orElseThrow(() -> new IllegalArgumentException("User not found with ID: " + dto.getUserId()));
                transactionHistory.setUser(newUser);
            }

            // Actualizar service
            if (dto.getServiceId() != null) { // Siempre busca si se proporciona un ID
                Services newService = servicesRepository.findById(dto.getServiceId())
                        .orElseThrow(() -> new IllegalArgumentException("Service not found with ID: " + dto.getServiceId()));
                transactionHistory.setService(newService);
            } else { // Si el DTO envía null para serviceId, desvincular el servicio
                transactionHistory.setService(null);
            }

            // Actualizar product
            if (dto.getProductId() != null) { // Siempre busca si se proporciona un ID
                Products newProduct = productsRepository.findById(dto.getProductId())
                        .orElseThrow(() -> new IllegalArgumentException("Product not found with ID: " + dto.getProductId()));
                transactionHistory.setProduct(newProduct);
            } else { // Si el DTO envía null para productId, desvincular el producto
                transactionHistory.setProduct(null);
            }


            transactionHistory.setTotalAmount(dto.getTotalAmount());
            transactionHistory.setStatus(dto.getStatus());
            transactionHistory.setCreatedAt(dto.getCreatedAt());
            transactionHistory.setQuantity(dto.getQuantity());
            transactionHistory.setAmountPerUnit(dto.getAmountPerUnit());
            transactionHistory.setDetail(dto.getDetail());

            TransactionHistory updatedTransaction = transactionHistoryRepository.save(transactionHistory);
            return convertToDto(updatedTransaction);
        }
        return null;
    }

    @Transactional
    public boolean deleteTransactionHistory(Integer id) {
        if (transactionHistoryRepository.existsById(id)) {
            transactionHistoryRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public List<TransactionHistoryDto> getTransactionHistoriesByUserId(Integer userId) {
        List<TransactionHistory> transactions = transactionHistoryRepository.findByUserUserId(userId);
        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public List<TransactionHistoryDto> getTransactionHistoriesByProviderId(Integer providerId) {
        List<TransactionHistory> transactions = transactionHistoryRepository.findByProviderId(providerId);
        return transactions.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Actualizar el estado de una transacción
    @Transactional
    public TransactionHistoryDto updateTransactionStatus(Integer id, String status) {
        Optional<TransactionHistory> transactionOptional = transactionHistoryRepository.findById(id);
        if (transactionOptional.isPresent()) {
            TransactionHistory transactionHistory = transactionOptional.get();
            transactionHistory.setStatus(status);
            TransactionHistory updatedTransaction = transactionHistoryRepository.save(transactionHistory);
            return convertToDto(updatedTransaction);
        }
        throw new IllegalArgumentException("Transaction not found with ID: " + id);
    }

    // Método de conversión de entidad Users a UsersDto
    private UsersDto convertUserToDto(Users user) {
        if (user == null) {
            return null;
        }

        String imageUrl = null;
        if (user.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(user.getImage().getFileName());
        }

        Integer rolId = (user.getRol() != null) ? user.getRol().getRolId() : null;
        Integer imageId = (user.getImage() != null) ? user.getImage().getImageId() : null;

        Integer providerId = null;
        Optional<Providers> provider = providersRepository.findByUser_UserId(user.getUserId());
        if (provider.isPresent()) {
            providerId = provider.get().getProviderId();
        }

        return new UsersDto(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                null,
                user.getPhoneNumber(),
                user.getLocation(),
                user.getPreferredLanguage(),
                user.getLastLogin(),
                user.getCreatedAt(),
                user.getStatus(),
                rolId,
                imageId,
                imageUrl,
                providerId
        );
    }
    private ProvidersDto convertProviderToDto(Providers provider) {
        if (provider == null) {
            return null;
        }

        String imageUrl = null;
        if (provider.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(provider.getImage().getFileName());
        }

        return new ProvidersDto(
                provider.getProviderId(),
                provider.getUser().getUserId(),
                provider.getName(),
                provider.getDescription(),
                provider.getAddress(),
                imageUrl,
                provider.getRating(),
                provider.getCreatedAt(),
                provider.getUpdatedAt(),
                provider.getStatus(),
                0,
                0,
                provider.getReviews(),
                provider.getCity(),
                provider.getCountry(),
                provider.getVerified(),
                provider.getPhone()
        );
    }

    // Método de conversión de entidad Services a ServicesDto
    private ServicesDto convertServiceToDto(Services service) {
        if (service == null) {
            return null;
        }

        String imageUrl = null;
        if (service.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(service.getImage().getFileName());
        }
        ProvidersDto providersDto = convertProviderToDto(service.getProvider());

        // Asumo que Provider, Category, SubSubCategoria tienen get[Id] y están cargados si es necesario
        Integer providerId = (service.getProvider() != null) ? service.getProvider().getProviderId() : null;
        Integer categoryId = (service.getCategory() != null) ? service.getCategory().getCategoryId() : null;
        Integer subSubCategoriaId = (service.getSubSubCategoria() != null) ? service.getSubSubCategoria().getSubSubCategoriaId() : null;

        return new ServicesDto(
                service.getServiceId(),
                service.getServiceName(),
                service.getPrice(),
                service.getDuration(),
                service.getDescription(),
                service.getCreatedAt(),
                service.getStatus(),
                providerId,
                providersDto,
                service.getImage() != null ? service.getImage().getImageId() : null,
                imageUrl,
                service.getTipoAtencion(),
                subSubCategoriaId,
                service.getOnSale(),
                categoryId
        );
    }

    // Método de conversión de entidad Products a ProductsDto
    private ProductsDto convertProductToDto(Products product) {
        if (product == null) {
            return null;
        }

        String imageUrl = null;
        if (product.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(product.getImage().getFileName());
        }
        ProvidersDto providersDto = convertProviderToDto(product.getProvider());

        // Asumo que Provider, Category, SubSubCategoria tienen get[Id] y están cargados si es necesario
        Integer providerId = (product.getProvider() != null) ? product.getProvider().getProviderId() : null;
        Integer categoryId = (product.getCategory() != null) ? product.getCategory().getCategoryId() : null;
        Integer subSubCategoriaId = (product.getSubSubCategoria() != null) ? product.getSubSubCategoria().getSubSubCategoriaId() : null;

        return new ProductsDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getStatus(),
                providerId,
                providersDto,
                categoryId,
                product.getImage() != null ? product.getImage().getImageId() : null,
                imageUrl,
                subSubCategoriaId,
                product.getIsOnSale()
        );
    }


    // Método de conversión de entidad TransactionHistory a TransactionHistoryDto
    private TransactionHistoryDto convertToDto(TransactionHistory transactionHistory) {
        UsersDto usersDto = convertUserToDto(transactionHistory.getUser());
        ServicesDto servicesDto = convertServiceToDto(transactionHistory.getService()); // Convierte el servicio
        ProductsDto productsDto = convertProductToDto(transactionHistory.getProduct()); // Convierte el producto

        return new TransactionHistoryDto(
                transactionHistory.getTransactionHistoryId(),
                transactionHistory.getTotalAmount(),
                transactionHistory.getStatus(),
                transactionHistory.getCreatedAt(),
                transactionHistory.getUser() != null ? transactionHistory.getUser().getUserId() : null, // ID de usuario
                usersDto,
                transactionHistory.getService() != null ? transactionHistory.getService().getServiceId() : null, // ID de servicio
                servicesDto, // Objeto DTO de servicio
                transactionHistory.getProduct() != null ? transactionHistory.getProduct().getProductId() : null, // ID de producto
                productsDto, // Objeto DTO de producto
                transactionHistory.getQuantity(),
                transactionHistory.getAmountPerUnit(),
                transactionHistory.getDetail()
        );
    }
}