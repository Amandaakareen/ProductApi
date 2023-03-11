package com.example.productapi.DTO;


import com.example.productapi.entity.AttributeEntity;

import lombok.Data;

@Data
public class AttributesDTO {
    
    private Long id;
    private String name;
    private String value;

    public static AttributesDTO mapTo(AttributeEntity attributeEntity){
        AttributesDTO dto = new AttributesDTO();
        
        dto.setId(attributeEntity.getId());
        dto.setName(attributeEntity.getName());
        dto.setValue(attributeEntity.getValue());

        return dto;
    }
    

   
}