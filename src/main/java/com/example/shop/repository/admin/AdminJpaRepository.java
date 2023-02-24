package com.example.shop.repository.admin;

import com.example.shop.entities.Admin;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminJpaRepository extends JpaRepository<Admin, Long> {
}
