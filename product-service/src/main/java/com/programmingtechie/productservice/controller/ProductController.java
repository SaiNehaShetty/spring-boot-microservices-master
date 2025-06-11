package com.programmingtechie.productservice.controller;

import com.programmingtechie.productservice.dto.ProductRequest;
import com.programmingtechie.productservice.dto.ProductResponse;
import com.programmingtechie.productservice.model.Product;
import com.programmingtechie.productservice.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/product")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createProduct(@RequestBody ProductRequest productRequest) {
        productService.createProduct(productRequest);
    }

    @GetMapping("/getall")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/getlist")
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getListedProducts(@RequestBody List<String> productIds){
        return productService.getListedProducts(productIds);
    }

    @GetMapping("/get")
    @ResponseStatus(HttpStatus.OK)
    public Optional<ProductResponse> getProduct(@RequestParam String productId){
        return productService.getProduct(productId);
    }

    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProductResponse updateProductById(@RequestBody Product product){
        return productService.updateProductById(product);
    }

    @DeleteMapping("/delete")
    @ResponseStatus(HttpStatus.OK)
    public void deleteProductById(@RequestParam String productId){
        productService.deleteProductById(productId);
    }

}