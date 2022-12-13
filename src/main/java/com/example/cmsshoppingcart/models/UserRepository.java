package com.example.cmsshoppingcart.models;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.cmsshoppingcart.models.data.User;

/**
 * UserRepository
 */
public interface UserRepository extends JpaRepository<User, Integer>{

    User findByUsername(String username);
}