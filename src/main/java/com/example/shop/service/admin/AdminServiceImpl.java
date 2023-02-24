package com.example.shop.service.admin;

import com.example.shop.dto.AdminDto;
import com.example.shop.entities.Admin;
import com.example.shop.entities.User;
import com.example.shop.repository.admin.AdminJpaRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;


@Service
@AllArgsConstructor
public class AdminServiceImpl implements AdminService {
    private final AdminJpaRepository adminJpaRepository;

    @Override
    public List<AdminDto> findAll() {
        return adminJpaRepository.findAll()
                .stream()
                .map(this::toAdminDto)
                .toList();
    }

    @Override
    public AdminDto toAdminDto(User user) {
        return AdminDto.builder()
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .userName(user.getUserName())
                .build();
    }

    public AdminDto toAdminDto(Admin admin) {
        return AdminDto.builder()
                .firstName(admin.getFirstName())
                .lastName(admin.getLastName())
                .userName(admin.getUserName())
                .build();
    }

}
