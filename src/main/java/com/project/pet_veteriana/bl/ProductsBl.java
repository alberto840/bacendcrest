package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.ImageS3Dto;
import com.project.pet_veteriana.dto.ProductsDto;
import com.project.pet_veteriana.dto.ProvidersDto;
import com.project.pet_veteriana.entity.*;
import com.project.pet_veteriana.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductsBl {

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private ProvidersRepository providersRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private OffersProductsRepository offersProductsRepository;

    @Autowired
    private SubSubCategoriaRepository subSubCategoriaRepository;

    @Autowired
    private ImageS3Repository imageS3Repository;

    @Autowired
    private ImagesS3Bl imagesS3Bl;

    @Transactional
    public ProductsDto createProductWithImage(ProductsDto productsDto, MultipartFile file) throws Exception {
        Providers provider = null;
        if (productsDto.getProviderId() != null) {
            provider = providersRepository.findById(productsDto.getProviderId())
                    .orElseThrow(() -> new IllegalArgumentException("Provider not found with ID: " + productsDto.getProviderId()));
        }

        Category category = categoryRepository.findById(productsDto.getCategoryId())
                .orElseThrow(() -> new IllegalArgumentException("Categoría no encontrada"));

        ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);

        ImageS3 image = new ImageS3();
        image.setImageId(imageDto.getImageId());
        image.setFileName(imageDto.getFileName());
        image.setFileType(imageDto.getFileType());
        image.setSize(imageDto.getSize());
        image.setUploadDate(imageDto.getUploadDate());
        image = imageS3Repository.save(image);

        SubSubCategoria subSubCategoria = null;
        if (productsDto.getSubSubCategoriaId() != null) {
            subSubCategoria = subSubCategoriaRepository.findById(productsDto.getSubSubCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("SubSubCategoria no encontrada"));
        }

        Products product = new Products();
        product.setName(productsDto.getName());
        product.setDescription(productsDto.getDescription());
        product.setPrice(productsDto.getPrice());
        product.setStock(productsDto.getStock());
        product.setStatus(productsDto.getStatus());
        product.setProvider(provider);
        product.setCategory(category);
        product.setImage(image);
        product.setSubSubCategoria(subSubCategoria);

        Products savedProduct = productsRepository.save(product);
        return convertToDto(savedProduct);
    }

    // Obtener todos los productos
    public List<ProductsDto> getAllProducts() {
        List<Products> products = productsRepository.findAll();
        return products.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener producto por ID
    public Optional<ProductsDto> getProductById(Integer productId) {
        Optional<Products> product = productsRepository.findById(productId);
        return product.map(this::convertToDto);
    }

    // Actualizar un producto con imagen
    @Transactional
    public Optional<ProductsDto> updateProductWithImage(Integer productId, ProductsDto productsDto, MultipartFile file) throws Exception {
        Optional<Products> existingProduct = productsRepository.findById(productId);
        if (existingProduct.isEmpty()) {
            throw new IllegalArgumentException("Producto no encontrado");
        }

        Products product = existingProduct.get();

        if (file != null && !file.isEmpty()) {
            ImageS3Dto imageDto = imagesS3Bl.uploadFile(file);
            product.setImage(new ImageS3(imageDto.getImageId(), imageDto.getFileName(), imageDto.getFileType(), imageDto.getSize(), imageDto.getUploadDate()));
        }

        product.setName(productsDto.getName());
        product.setDescription(productsDto.getDescription());
        product.setPrice(productsDto.getPrice());
        product.setStock(productsDto.getStock());
        product.setStatus(productsDto.getStatus());
        product.setIsOnSale(productsDto.getIsOnSale());

        SubSubCategoria subSubCategoria = null;
        if (productsDto.getSubSubCategoriaId() != null) {
            subSubCategoria = subSubCategoriaRepository.findById(productsDto.getSubSubCategoriaId())
                    .orElseThrow(() -> new IllegalArgumentException("SubSubCategoria no encontrada"));
        }
        product.setSubSubCategoria(subSubCategoria);

        Products updatedProduct = productsRepository.save(product);
        return Optional.of(convertToDto(updatedProduct));
    }


    // Eliminar un producto
    @Transactional
    public boolean deleteProduct(Integer productId) {
        Optional<Products> productOptional = productsRepository.findById(productId);

        if (productOptional.isPresent()) {
            Products product = productOptional.get();

            // Eliminar todas las referencias en offers_products antes de eliminar el producto
            offersProductsRepository.deleteByProduct(product);

            // Verificar si el producto tiene una imagen antes de eliminarla
            if (product.getImage() != null) {
                try {
                    imagesS3Bl.deleteFile(product.getImage().getImageId());
                } catch (Exception e) {
                    throw new RuntimeException("Error al eliminar la imagen asociada", e);
                }
            }

            // Establecer la imagen como null antes de eliminar el producto
            product.setImage(null);
            productsRepository.save(product);

            // Ahora eliminar el producto
            productsRepository.delete(product);
            return true;
        }

        return false;
    }




    // Obtener productos recientes (últimos 10 creados)
    public List<ProductsDto> getRecentProducts() {
        return productsRepository.findTop10ByOrderByCreatedAtDesc()
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener productos en oferta
    public List<ProductsDto> getProductsOnOffer() {
        LocalDateTime now = LocalDateTime.now();
        return offersProductsRepository.findByOffer_StartDateBeforeAndOffer_EndDateAfterAndOffer_IsActiveTrue(now, now)
                .stream()
                .map(offersProduct -> convertToDto(offersProduct.getProduct()))
                .collect(Collectors.toList());
    }

    // Obtener productos por proveedor
    public List<ProductsDto> getProductsByProvider(Integer providerId) {
        Providers provider = providersRepository.findById(providerId)
                .orElseThrow(() -> new RuntimeException("Provider not found"));

        return productsRepository.findByProvider(provider)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener productos por usuario (buscando el proveedor correspondiente)
    public List<ProductsDto> getProductsByUserId(Integer userId) {
        // Buscar proveedor asociado al usuario
        Providers provider = providersRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("No provider found for userId: " + userId));

        // Obtener los productos de ese proveedor
        return productsRepository.findByProvider(provider)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // Obtener productos por categoría
    public List<ProductsDto> getProductsByCategory(Integer categoryId) {
        // Buscar la categoría en la base de datos
        Category category = categoryRepository.findById(categoryId)
                .orElseThrow(() -> new RuntimeException("Category not found with ID: " + categoryId));

        // Buscar productos de la categoría
        return productsRepository.findByCategory(category)
                .stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
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

    // Convertir entidad a DTO
    private ProductsDto convertToDto(Products product) {
        ProvidersDto providersDto = convertProviderToDto(product.getProvider());
        String imageUrl = null;
        if (product.getImage() != null) {
            imageUrl = imagesS3Bl.generateFileUrl(product.getImage().getFileName());
        }
        return new ProductsDto(
                product.getProductId(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getStock(),
                product.getCreatedAt(),
                product.getStatus(),
                product.getProvider() != null ? product.getProvider().getProviderId() : null,
                providersDto,
                product.getCategory() != null ? product.getCategory().getCategoryId() : null,
                product.getImage() != null ? product.getImage().getImageId() : null,
                imageUrl,
                product.getSubSubCategoria() != null ? product.getSubSubCategoria().getSubSubCategoriaId() : null,
                product.getIsOnSale()
        );
    }
}
