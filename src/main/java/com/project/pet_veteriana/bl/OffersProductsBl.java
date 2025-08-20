package com.project.pet_veteriana.bl;

import com.project.pet_veteriana.dto.OffersProductsDto;
import com.project.pet_veteriana.entity.Offers;
import com.project.pet_veteriana.entity.OffersProducts;
import com.project.pet_veteriana.entity.Products;
import com.project.pet_veteriana.repository.OffersProductsRepository;
import com.project.pet_veteriana.repository.OffersRepository;
import com.project.pet_veteriana.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OffersProductsBl {

    @Autowired
    private OffersProductsRepository offersProductsRepository;

    @Autowired
    private OffersRepository offersRepository;

    @Autowired
    private ProductsRepository productsRepository;

    // Crear una relación OffersProducts}
    @Transactional
    public OffersProductsDto createOffersProducts(OffersProductsDto dto) {
        Optional<Offers> offer = offersRepository.findById(dto.getOfferId());
        Optional<Products> product = productsRepository.findById(dto.getProductId());

        if (offer.isEmpty()) {
            throw new IllegalArgumentException("Offer not found");
        }

        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        OffersProducts offersProducts = new OffersProducts();
        offersProducts.setOffer(offer.get());
        offersProducts.setProduct(product.get());

        OffersProducts savedEntity = offersProductsRepository.save(offersProducts);
        return convertToDto(savedEntity);
    }

    // Obtener todas las relaciones OffersProducts
    public List<OffersProductsDto> getAllOffersProducts() {
        List<OffersProducts> entities = offersProductsRepository.findAll();
        return entities.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    // Obtener una relación OffersProducts por ID
    public OffersProductsDto getOffersProductsById(Integer id) {
        OffersProducts offersProducts = offersProductsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OffersProducts not found"));
        return convertToDto(offersProducts);
    }

    // Actualizar una relación OffersProducts
    @Transactional
    public OffersProductsDto updateOffersProducts(Integer id, OffersProductsDto dto) {
        OffersProducts offersProducts = offersProductsRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("OffersProducts not found"));

        Optional<Offers> offer = offersRepository.findById(dto.getOfferId());
        Optional<Products> product = productsRepository.findById(dto.getProductId());

        if (offer.isEmpty()) {
            throw new IllegalArgumentException("Offer not found");
        }

        if (product.isEmpty()) {
            throw new IllegalArgumentException("Product not found");
        }

        offersProducts.setOffer(offer.get());
        offersProducts.setProduct(product.get());

        OffersProducts updatedEntity = offersProductsRepository.save(offersProducts);
        return convertToDto(updatedEntity);
    }

    // Eliminar una relación OffersProducts
    @Transactional
    public boolean deleteOffersProducts(Integer id) {
        if (offersProductsRepository.existsById(id)) {
            offersProductsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Convertir entidad a DTO
    private OffersProductsDto convertToDto(OffersProducts entity) {
        return new OffersProductsDto(
                entity.getOffersProductsId(),
                entity.getOffer().getOfferId(),
                entity.getProduct().getProductId()
        );
    }
}
