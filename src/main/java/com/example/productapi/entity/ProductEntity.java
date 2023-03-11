package com.example.productapi.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.example.productapi.Response.ProductRequest;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor

@Entity
@Table(name = "PRODUCTS")
public class ProductEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private Long id;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "SKU")
    private String sku;

    @Column(name = "BARCODES")
    private String barcodes;

    @Column(name = "DESCRIPTIONS")
    private String description;
    
    @OneToMany(mappedBy = "idProductEntity", cascade = CascadeType.ALL)
    private List<AttributeEntity> attributes;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CREATED")
    private Date created;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;


    public static ProductEntity mapTo(ProductRequest productRequest){
        ProductEntity product = new ProductEntity();
            product.setTitle(productRequest.getTitle());
            product.setBarcodes(productRequest.getBarcodes());
            product.setDescription(productRequest.getDescription());
            product.setPrice(productRequest.getPrice());
            product.setSku(productRequest.getSku());
            product.setCreated(new Date());

            List<AttributeEntity> attributes = productRequest
                .getAttributes()
                .stream()
                .map(a -> AttributeEntity.mapTo(a))
                .toList();
                
            product.setAttributes(attributes);
            return product;
    }
    
}