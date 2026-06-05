package com.springworks.product_service.service.impl;


import com.springworks.product_service.dto.request.ProductRequestDto;
import com.springworks.product_service.dto.request.ProductUpdateRequestDto;
import com.springworks.product_service.dto.response.CategoryCountDto;
import com.springworks.product_service.dto.response.CompanyCountDto;
import com.springworks.product_service.dto.response.ProductResponseDto;
import com.springworks.product_service.dto.response.StatusCountDto;
import com.springworks.product_service.entity.Product;
import com.springworks.product_service.enums.Category;
import com.springworks.product_service.enums.Status;
import com.springworks.product_service.exception.ProductNotFoundException;
import com.springworks.product_service.repository.ProductRepository;
import com.springworks.product_service.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor

public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;

     /*
     -------------------------------------------------------
                    CREATE PRODUCT
     -------------------------------------------------------
     */

    @Override
    public ProductResponseDto save(
            ProductRequestDto requestDto) {

        Product product =
                modelMapper.map(
                        requestDto,
                        Product.class
                );

        product.setRating(0F);
        product.setReviewsCount(0);
        product.setCreatedDate(LocalDateTime.now());
        product.setUpdatedDate(null);

        Product savedProduct =
                productRepository.save(product);

        return mapToResponseDto(savedProduct);
    }

    /*
     -------------------------------------------------------
                    GET PRODUCT BY ID
     -------------------------------------------------------
     */

    @Override
    public ProductResponseDto getById(
            String productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product Not Found With Id : "
                                                + productId
                                ));

        return mapToResponseDto(product);
    }

    /*
     -------------------------------------------------------
                    GET ALL PRODUCTS
     -------------------------------------------------------
     */

    @Override
    public List<ProductResponseDto> getAll() {

        return productRepository.findAll()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /*
     -------------------------------------------------------
                    UPDATE PRODUCT
     -------------------------------------------------------
     */

    @Override
    public ProductResponseDto update(
            String productId,
            ProductUpdateRequestDto productUpdateRequestDto) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product Not Found With Id : "
                                                + productId
                                ));

        modelMapper.map(productUpdateRequestDto, product);

        Product updatedProduct =
                productRepository.save(product);

        return mapToResponseDto(updatedProduct);
    }

    /*
     -------------------------------------------------------
                    DELETE PRODUCT
     -------------------------------------------------------
     */

    @Override
    public void delete(String productId) {

        Product product =
                productRepository.findById(productId)
                        .orElseThrow(() ->
                                new ProductNotFoundException(
                                        "Product Not Found With Id : "
                                                + productId
                                ));

        productRepository.delete(product);
    }

    /*
     -------------------------------------------------------
                    PAGINATION
     -------------------------------------------------------
     */

    @Override
    public Page<ProductResponseDto> getAllByPage(
            int page,
            int size,
            String sortBy) {

        Pageable pageable =
                PageRequest.of(
                        page,
                        size,
                        Sort.by(sortBy).descending()
                );

        Page<Product> productPage =
                productRepository.findAll(pageable);

        return productPage.map(this::mapToResponseDto);
    }

    /*
     -------------------------------------------------------
                    FILTER BY CATEGORY
     -------------------------------------------------------
     */

    @Override
    public List<ProductResponseDto> getByCategory(
            Category category) {

        return productRepository.findByCategory(category)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /*
     -------------------------------------------------------
                    FILTER BY STATUS
     -------------------------------------------------------
     */

    @Override
    public List<ProductResponseDto> getByStatus(
            Status status) {

        return productRepository.findByStatus(status)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /*
     -------------------------------------------------------
                    TOP RATED PRODUCTS
     -------------------------------------------------------
     */
    @Override
    public List<ProductResponseDto> getTopRatedProducts() {

        return productRepository
                .findTop10ByOrderByRatingDesc()
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }



    /*
     -------------------------------------------------------
                    SEARCH PRODUCTS
     -------------------------------------------------------
     */

    @Override
    public List<ProductResponseDto> searchProducts(
            String keyword) {

        return productRepository
                .findByNameContainingIgnoreCase(keyword)
                .stream()
                .map(this::mapToResponseDto)
                .toList();
    }

    /*
     -------------------------------------------------------
                    CATEGORY COUNT
     -------------------------------------------------------
     */

    @Override
    public List<CategoryCountDto> countProductsByCategory() {

        return productRepository.countProductsByCategory()
                .stream()
                .map(response -> CategoryCountDto.builder()
                        .category(response.getCategory())
                        .totalProducts(response.getTotalProducts())
                        .build())
                .toList();
    }

    /*
     -------------------------------------------------------
                    COMPANY COUNT
     -------------------------------------------------------
     */

    @Override
    public List<CompanyCountDto> countProductsByCompany() {

        return productRepository.countProductsByCompany()
                .stream()
                .map(response -> CompanyCountDto.builder()
                        .company(response.getCompany())
                        .totalProducts(response.getTotalProducts())
                        .build())
                .toList();
    }

    /*
     -------------------------------------------------------
                    STATUS COUNT
     -------------------------------------------------------
     */

    @Override
    public List<StatusCountDto> countProductsByStatus() {

        return productRepository.countProductsByStatus()
                .stream()
                .map(response -> StatusCountDto.builder()
                        .status(response.getStatus())
                        .totalProducts(response.getTotalProducts())
                        .build())
                .toList();
    }

    /*
     -------------------------------------------------------
                    COMMON DTO MAPPER
     -------------------------------------------------------
     */

    private ProductResponseDto mapToResponseDto(
            Product product) {

        ProductResponseDto responseDto =
                modelMapper.map(
                        product,
                        ProductResponseDto.class
                );

        int discountAmount =
                product.getMaxRetailPrice()
                        * product.getDiscountPercentage()
                        / 100;

        int sellingPrice =
                product.getMaxRetailPrice()
                        - discountAmount;

        responseDto.setSellingPrice(sellingPrice);

        return responseDto;
    }
}
