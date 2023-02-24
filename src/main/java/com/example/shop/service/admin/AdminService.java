package com.example.shop.service.admin;

import com.example.shop.dto.AdminDto;
import com.example.shop.entities.User;
import java.util.List;

public interface AdminService {
    List<AdminDto> findAll();
    AdminDto toAdminDto(User user);
}
