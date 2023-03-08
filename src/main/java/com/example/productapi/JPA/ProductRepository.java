package com.example.productapi.JPA;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.productapi.entity.ProductEntity;

@Repository
public interface ProductRepository extends JpaRepository<ProductEntity,Long>{
    

    @Query(value = "select p from ProductEntity p where " +
    "(:sku is not null and :barcode is not null and p.sku = :sku and p.barcodes = :barcode) " +  
    "or (:sku is not null and :barcode is null and p.sku = :sku) " +
    "or (:barcode is not null and :sku is null and p.barcodes = :barcode) " +
    "or (:sku is null and :barcode is null and 1=1)")
    Page<ProductEntity> findAllProductBySkuByBarcode(PageRequest pageRequest, String sku, String barcode);

    @Query(value = "select count(p) from ProductEntity p")
    Long coutTotal();
}