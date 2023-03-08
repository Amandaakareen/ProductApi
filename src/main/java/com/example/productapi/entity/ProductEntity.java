package com.example.productapi.entity;

import java.util.Date;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;



import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
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

    @OneToMany(mappedBy = "idProductEntity")
    private List<AttributeEntity> attributes;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "CREATED")
    private Date created;

    @Column(name = "LAST_UPDATED")
    private Date lastUpdated;


    
}