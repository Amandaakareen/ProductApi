package com.example.productapi.controller;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.productapi.DTO.ProductDTO;
import com.example.productapi.DTO.ProductListDTO;
import com.example.productapi.Response.ProductRequest;
import com.example.productapi.entity.ProductEntity;
import com.example.productapi.error.Exception.BadRequestException;
import com.example.productapi.error.Exception.InternalServerErrorException;
import com.example.productapi.error.Exception.NotFoundException;
import com.example.productapi.error.Exception.NotFoundProductException;
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
    ) throws Exception{
        
        try{
            ProductListDTO listProduto = new ProductListDTO();
            Page<ProductEntity> productAll = productsService.getProductAll(start,num, sku, barcode, fields);

            if(fields == null){
                List<Map<String, Object>> list =  productAll.stream().map(product -> mapDtoFieldsEmpty(ProductDTO.mapTo(product))).toList();
                listProduto.setItems(list);
            }else{
                List<Map<String, Object>> list =  productAll.stream().map(product -> mapDtoFields(ProductDTO.mapTo(product),fields)).toList();
                listProduto.setItems(list);           
            }

            listProduto.setTotal(productsService.total());
            
                 
            return listProduto;

        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }catch(Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @GetMapping("{id}")
    public Map<String, Object>  getById(@PathVariable("id") Long id, @RequestParam(required = false)  List<String> fields){
        try{
            ProductEntity productById = productsService.getProductById(id); 
            
            if(fields == null){
                Map<String, Object> product =  mapDtoFieldsEmpty(ProductDTO.mapTo(productById) );
                return product;
            }else{
                Map<String, Object> product = mapDtoFields(ProductDTO.mapTo(productById),fields);
                return product;         
            }
        }catch(NotFoundException e){
            throw new NotFoundProductException(id);
        }catch(BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }catch(Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
        
    }

    @PostMapping
    public Long addProduct(@RequestBody @Valid ProductRequest productRequest){
        try{
            ProductEntity product = ProductEntity.mapTo(productRequest);
            ProductEntity productA = productsService.saveProduct(product);
               
            return productA.getId();

        }catch(NotFoundException e){
            throw new NotFoundException(e.getMessage());
        }catch(BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }catch(Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @PutMapping("{id}")
    public boolean toEditProduct(@PathVariable("id") Long id, @RequestBody ProductRequest productRequest){
        try{
            ProductEntity product = ProductEntity.mapTo(productRequest);
            boolean editProduct = productsService.editProduct(id, product);

            return editProduct;
           
        }catch(NotFoundException e){
            throw new NotFoundProductException(id);
        }catch(BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }catch(Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
    }

    @DeleteMapping("{id}")
    public Boolean deleteProduct(@PathVariable("id") Long id){
        try{
            Boolean product = productsService.deleteById(id);
            return product;
        }catch(NotFoundProductException e){
            throw new NotFoundProductException(id);
        }catch(BadRequestException e){
            throw new BadRequestException(e.getMessage());
        }catch(Exception e){
            throw new InternalServerErrorException(e.getMessage());
        }
       
    }

    public Map<String, Object> mapDtoFields(ProductDTO product, List<String> fields) {
        Map<String, Object> map = new HashMap<>();
        
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

    public Map<String, Object> mapDtoFieldsEmpty(ProductDTO product){
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