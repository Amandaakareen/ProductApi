package com.example.productapi.service;



import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;


import com.example.productapi.JPA.ProductRepository;
import com.example.productapi.entity.ProductEntity;

@Service
public class ProductsService {

    @Autowired
    ProductRepository productRepository;

    public Page<ProductEntity>  getProductAll( int start, int num, 
    String sku, String barcode, List<String> fields ){
        PageRequest pageRequest = PageRequest.of(start, num);
        return productRepository.findAllProductBySkuByBarcode(pageRequest, sku, barcode);
        
    }

    public Long total(){
        Long total = productRepository.coutTotal();
        return total;
    }  

}
