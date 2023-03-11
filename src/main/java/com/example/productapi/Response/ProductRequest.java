package com.example.productapi.Response;


import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProductRequest {
    
    private String title;
    @NotNull @NotBlank
    private String sku;
    private String barcodes;
    private String description;
    private List<AttributeRequest> attributes;
    private Double price;
    private Date created;
    private Date lastUpdated;
}
