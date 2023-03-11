package com.example.productapi.DTO;

import java.util.Date;
import java.util.List;


import com.example.productapi.entity.ProductEntity;

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

    public static ProductDTO mapTo(ProductEntity productEntity){
        ProductDTO dto = new ProductDTO();

        dto.setId(productEntity.getId());
        dto.setTitle(productEntity.getTitle());
        dto.setSku(productEntity.getSku());
        dto.setBarcodes(productEntity.getBarcodes());
        dto.setDescription(productEntity.getDescription());
        dto.setPrice(productEntity.getPrice());
        dto.setCreated(productEntity.getCreated());
        dto.setLastUpdated(productEntity.getLastUpdated());
        dto.setAttributes(productEntity.getAttributes().stream().map(a -> AttributesDTO.mapTo(a)).toList());
        
        return dto;
    }

    
}