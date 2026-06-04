package com.springworks.product_service.dto.request;


import com.springworks.product_service.enums.Category;

public class ProductUpdateRequestDto {
    private String id;
    private String name;
    private int maxRetailPrice;
    private byte discountPercentage;
    private String brand;
    private Category category;
    private String company;
}
