package com.example.productapi.controller;



import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.productapi.DTO.ProductListDTO;
import com.example.productapi.entity.ProductEntity;
import com.example.productapi.service.ProductsService;


@RestController
@RequestMapping("/api/products")
public class ProductsController {
    
    @Autowired
    ProductsService productsService;

    @GetMapping
    public ProductListDTO allProducts(
        @RequestParam(defaultValue = "0", required = false) int start,
        @RequestParam(defaultValue = "5", required = false) int num,
        @RequestParam(required = false) String sku,
        @RequestParam(required = false) String barcode, 
        @RequestParam(required = false) List<String> fields
    ){
        
        try{
            ProductListDTO listProduto = new ProductListDTO();
            Page<ProductEntity> productAll = productsService.getProductAll(start,num, sku, barcode, fields);

            if(fields == null){
                List<Map<String, Object>> list =  productAll.stream().map(product -> mapDtoFieldsEmpty(product)).toList();
                listProduto.setItems(list);
            }else{
                List<Map<String, Object>> list =  productAll.stream().map(product -> mapDtoFields(product,fields)).toList();
                listProduto.setItems(list);           
            }

            listProduto.setTotal(productsService.total());
            return listProduto;

        }catch(RuntimeException e){
            throw new RuntimeException(e);
        }
    }

    public Map<String, Object> mapDtoFields(ProductEntity product, List<String> fields) {
        Map<String, Object> map = new HashMap<>();

        /*Stream.of(ProductEntity.class.getDeclaredFields())
        .filter(classField -> fields.contains(classField.getName()) || fields == null)
        .forEach(classField -> {
            try {
                classField.setAccessible(true);
                map.put(classField.getName(), classField.get(product));
            } catch(Exception e) {
                e.printStackTrace();
            }
          }
        );*/

        
        if(fields.contains("id")){map.put("id", product.getId());}
        if(fields.contains("title")){map.put("title", product.getTitle());}
        if(fields.contains("sku")){map.put("sku", product.getSku());}
        if(fields.contains("barcode")){map.put("barcode", product.getBarcodes());}
        if(fields.contains("description")){map.put("description", product.getDescription());}
        if(fields.contains("attributes")){map.put("attributes", product.getAttributes());}
        if(fields.contains("price")){map.put("price", product.getPrice());}
        if(fields.contains("created")){map.put("created", product.getCreated());}
        if(fields.contains("lastUpdated")){map.put("lastUpdated", product.getLastUpdated());}
        return map;
    }

    

    public Map<String, Object> mapDtoFieldsEmpty(ProductEntity product){
        Map<String, Object> map = new HashMap<>();
        map.put("id", product.getId());
        map.put("title", product.getTitle());
        map.put("sku", product.getSku());
        map.put("barcode", product.getBarcodes());
        map.put("description", product.getDescription());
        map.put("attributes", product.getAttributes());
        map.put("price", product.getPrice());
        map.put("created", product.getCreated());
        map.put("lastUpdated", product.getLastUpdated());
        return map;
    }

}