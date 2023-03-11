package com.example.productapi.DTO;



import java.util.List;
import java.util.Map;

import lombok.Data;

@Data
public class ProductListDTO {
    
    private Long total;
    private List<Map<String, Object>> items;
}