package com.example.cmsshoppingcart.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cmsshoppingcart.models.data.Admin;

/**
 * AdminRepository
 */
public interface AdminRepository extends JpaRepository<Admin, Integer>{

    Admin findByUsername(String username);
}