package com.springworks.product_service.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
public class ProductController {
    private final com.springworks.product_service.service.ProductService productService;

     /*
     -------------------------------------------------------
                    CREATE PRODUCT
     -------------------------------------------------------
     */

    @PostMapping
    public ResponseEntity<com.springworks.product_service.dto.response.ProductResponseDto> save(
            @RequestBody com.springworks.product_service.dto.request.ProductRequestDto requestDto) {

        return ResponseEntity.status(HttpStatus.CREATED).body(productService.save(requestDto));
    }

    /*
     -------------------------------------------------------
                    GET PRODUCT BY ID
     -------------------------------------------------------
     */

    @GetMapping("/{productId}")
    public ResponseEntity<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto> getProductById(
            @PathVariable String productId) {
        return ResponseEntity.ok(productService.getById(productId));
    }

    /*
     -------------------------------------------------------
                    GET ALL PRODUCTS
     -------------------------------------------------------
     */

    @GetMapping
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto>> getAll() {
        return ResponseEntity.ok(productService.getAll());
    }

    /*
     -------------------------------------------------------
                    UPDATE PRODUCT
     -------------------------------------------------------
     */

    @PutMapping("/{productId}")
    public ResponseEntity<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto> update(
            @PathVariable String productId,
            @RequestBody com.trainingmug.ecommerce.productservice.dto.request.ProductUpdateRequestDto requestDto) {

        return ResponseEntity.ok(productService.update(
                productId,
                requestDto
        ));
    }

    /*
     -------------------------------------------------------
                    DELETE PRODUCT
     -------------------------------------------------------
     */

    @DeleteMapping("/{productId}")
    public ResponseEntity<?> delete(@PathVariable String productId) {
        productService.delete(productId);
        return ResponseEntity.ok().build();
    }

    /*
     -------------------------------------------------------
                    PAGINATION & SORTING
     -------------------------------------------------------
     */

    @GetMapping("/pagination")
    public ResponseEntity<Page<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto>> getAllByPage(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {

        return ResponseEntity.ok(productService.getAllByPage(
                page,
                size,
                sortBy
        ));
    }

    /*
     -------------------------------------------------------
                    FILTER BY CATEGORY
     -------------------------------------------------------
     */

    @GetMapping("/category/{category}")
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto>> getProductsByCategory(
            @PathVariable Category category) {
        return ResponseEntity.ok(productService.getByCategory(category));
    }

    /*
     -------------------------------------------------------
                    FILTER BY STATUS
     -------------------------------------------------------
     */

    @GetMapping("/status/{status}")
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto>> getProductsByStatus(
            @PathVariable Status status) {

        return ResponseEntity.ok(productService.getByStatus(status));
    }

    /*
     -------------------------------------------------------
                    TOP RATED PRODUCTS
     -------------------------------------------------------
     */

    @GetMapping("/top-rated")
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto>> getTopRatedProducts() {
        return ResponseEntity.ok(productService.getTopRatedProducts());
    }

    /*
     -------------------------------------------------------
                    SEARCH PRODUCTS
     -------------------------------------------------------
     */

    @GetMapping("/search")
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.ProductResponseDto>> searchProducts(
            @RequestParam String keyword) {

        return ResponseEntity.ok(productService.searchProducts(keyword));
    }

    /*
     -------------------------------------------------------
                    CATEGORY COUNT
     -------------------------------------------------------
     */

    @GetMapping("/count-by-category")
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.CategoryCountDto>> countProductsByCategory() {
        return ResponseEntity.ok(productService.countProductsByCategory());
    }

    /*
     -------------------------------------------------------
                    COMPANY COUNT
     -------------------------------------------------------
     */

    @GetMapping("/count-by-company")
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.CompanyCountDto>> countProductsByCompany() {
        return ResponseEntity.ok(productService.countProductsByCompany());
    }

    /*
     -------------------------------------------------------
                    STATUS COUNT
     -------------------------------------------------------
     */

    @GetMapping("/count-by-status")
    public ResponseEntity<List<com.trainingmug.ecommerce.productservice.dto.response.StatusCountDto>> countProductsByStatus() {

        return ResponseEntity.ok(productService.countProductsByStatus());
    }
}
