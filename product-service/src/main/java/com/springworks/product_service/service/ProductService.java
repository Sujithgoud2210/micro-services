package com.springworks.product_service.service;


import com.springworks.product_service.dto.request.ProductRequestDto;
import com.springworks.product_service.dto.request.ProductUpdateRequestDto;
import com.springworks.product_service.dto.response.CategoryCountDto;
import com.springworks.product_service.dto.response.CompanyCountDto;
import com.springworks.product_service.dto.response.ProductResponseDto;
import com.springworks.product_service.dto.response.StatusCountDto;
import com.springworks.product_service.enums.Category;
import com.springworks.product_service.enums.Status;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ProductService {
    ProductResponseDto save(ProductRequestDto requestDto);

    ProductResponseDto getById(String productId);
    List<ProductResponseDto> getAll();
    ProductResponseDto update(
            String productId,
            ProductUpdateRequestDto productUpdateRequestDto
    );
    void delete(String productId);
    Page<ProductResponseDto> getAllByPage(
            int page,
            int size,
            String sortBy
    );

    List<ProductResponseDto> getByCategory(
            Category category
    );

    List<ProductResponseDto> getByStatus(
            Status status
    );

    List<ProductResponseDto> getTopRatedProducts();


    List<ProductResponseDto> searchProducts(
            String keyword
    );

    List<CategoryCountDto> countProductsByCategory();

    List<CompanyCountDto> countProductsByCompany();

    List<StatusCountDto> countProductsByStatus();
}
