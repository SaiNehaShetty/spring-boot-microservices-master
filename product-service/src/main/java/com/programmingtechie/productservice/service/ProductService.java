
package com.programmingtechie.productservice.service;

import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct(ProductRequest productRequest) {
        Product product = Product.builder()
                .name(productRequest.getName())
                .description(productRequest.getDescription())
                .price(productRequest.getPrice())
                .build();

        productRepository.save(product);
        log.info("Product {} is saved", product.getId());
    }

    public List<ProductResponse> getAllProducts() {
        List<Product> products = productRepository.findAll();

        return products.stream().map(this::mapToProductResponse).toList();
    }

    private ProductResponse mapToProductResponse(Product product) {
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .description(product.getDescription())
                .price(product.getPrice())
                .build();
    }
    public void deleteProductById(String productId) {
        if (productRepository.existsById(productId)) {
            productRepository.deleteProductById(productId);
            log.info("Product with ID {} has been deleted", productId);
        } else {
            log.warn("Attempted to delete non-existing product with ID {}", productId);
        }
    }

    public ProductResponse updateProductById(Product product) {
        Product existingProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException("Product not found with ID: " + product.getId()));

        existingProduct.setName(product.getName());
        existingProduct.setDescription(product.getDescription());
        existingProduct.setPrice(product.getPrice());

        Product updatedProduct = productRepository.save(existingProduct);

        return mapToProductResponse(updatedProduct);
    }

    public Optional<ProductResponse> getProduct(String productId) {
        return productRepository.findById(productId)
                .map(this::mapToProductResponse);
    }

    public List<ProductResponse> getListedProducts(List<String> productIds) {
        List<Product> products=productRepository.findAllById((productIds));

        List<String> foundIds = products.stream()
                .map(Product::getId)
                .toList();

        List<String> missingIds = productIds.stream()
                .filter(id -> !foundIds.contains(id))
                .toList();

        if (!missingIds.isEmpty()) {
            log.warn("Some products not found: {}", missingIds);
        }
        return products.stream()
                .map(this::mapToProductResponse)
                .toList();
    }
}