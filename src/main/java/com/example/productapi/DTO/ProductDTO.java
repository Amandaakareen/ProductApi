package com.example.productapi.DTO;

import java.util.Date;
import java.util.List;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductDTO {
    
    private Long id;
    private String title;
    private String sku;
    private String barcodes;
    private String description;
    private List<AttributesDTO> attributes;
    private Double price;
    private Date created;
    private Date lastUpdated;

    

    
}