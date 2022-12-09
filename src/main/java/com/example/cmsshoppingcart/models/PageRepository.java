package com.example.cmsshoppingcart.models;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.example.cmsshoppingcart.models.data.Page;

import java.util.List;

public interface PageRepository extends CrudRepository<Page, Integer>{
    @Override
    List<Page> findAll();

    Page findBySlug(String slug);

    Page getOne(int id);
    
    @Query("SELECT p FROM Page p WHERE p.id != :id and p.slug = :slug")
    Page findBySlug(int id, String slug);
}
