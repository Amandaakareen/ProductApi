package com.example.productapi.service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.example.productapi.JPA.AttributeRepository;
import com.example.productapi.JPA.ProductRepository;
import com.example.productapi.entity.AttributeEntity;
import com.example.productapi.entity.ProductEntity;
import com.example.productapi.error.Exception.NotFoundException;

@Service
public class ProductsService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    AttributeRepository attributeRepository;

    public Page<ProductEntity>  getProductAll( int start, int num, 
    String sku, String barcode, List<String> fields ){
        PageRequest pageRequest = PageRequest.of(start, num);
        return productRepository.findAllProductBySkuByBarcode(pageRequest, sku, barcode);
    }

    public Long total(){
        Long total = productRepository.coutTotal();
        return total;
    } 

    public ProductEntity getProductById(Long id){
        Optional<ProductEntity> product = productRepository.findById(id);

        if(product.isEmpty()){
            throw new NotFoundException("produto não existe");
        }

        return product.get();
        
    }

    @Transactional
    public ProductEntity saveProduct(ProductEntity product){
        ProductEntity productEntity =  productRepository.save(product);

        product.getAttributes().forEach(a -> {
            a.setIdProductEntity(productEntity);
            attributeRepository.save(a);
        });

        return productEntity;
    }

    public boolean editProduct(Long id, ProductEntity product){
        Optional<ProductEntity> productEntity =  productRepository.findById(id);

        if(productEntity.isEmpty()){
            throw new NotFoundException("produto não existe");
        }

        ProductEntity productMap = productEntity.get();
        productMap.setTitle(product.getTitle());
        productMap.setDescription(product.getDescription());
        productMap.setPrice(product.getPrice());
        productMap.setSku(product.getSku());
        productMap.setLastUpdated(new Date());
        productMap.setBarcodes(product.getBarcodes());

        productRepository.save(productMap);

        List<Long> listId = product.getAttributes().stream().map(a -> a.getId()).toList();
        List<AttributeEntity> attributes =  attributeRepository.getByIdProductById(productMap.getId(), listId);

       
        product.getAttributes().forEach(p -> {
            attributes.forEach(a ->{
                if(a.getId() == p.getId()){
                    a.setName(p.getName());
                    a.setValue(p.getValue());

                    attributeRepository.save(a);
                }
            });
        });

        return true;
    }

    public boolean deleteById(Long id){
        productRepository.deleteById(id);
        
        return true;
    }
}
