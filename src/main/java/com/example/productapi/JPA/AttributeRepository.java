package com.example.productapi.JPA;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.productapi.entity.AttributeEntity;

@Repository
public interface AttributeRepository extends JpaRepository<AttributeEntity, Long> {
    
    @Query(value = "select a from AttributeEntity a where a.id in(:listId) and a.idProductEntity.id = :productId")
    public List<AttributeEntity> getByIdProductById(Long productId,List<Long> listId);
}
