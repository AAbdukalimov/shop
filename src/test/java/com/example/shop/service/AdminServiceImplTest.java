package com.example.shop.service;

import com.example.shop.dto.AdminDto;
import com.example.shop.entities.Admin;
import com.example.shop.repository.admin.AdminJpaRepository;
import com.example.shop.service.admin.AdminServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdminServiceImplTest {

    @Mock
    private AdminJpaRepository adminJpaRepository;
    @InjectMocks
    private AdminServiceImpl adminService;
    private List<AdminDto> expected;
    private List<Admin> admins;

    @BeforeEach
    public void init() {
        Admin firstAdmin = Admin.builder()
                .id(1L)
                .firstName("test")
                .lastName("test")
                .userName("test_1")
                .build();
        Admin secondAdmin = Admin.builder()
                .id(2L)
                .firstName("test2")
                .lastName("test2")
                .userName("test_2")
                .build();

        admins = List.of(firstAdmin, secondAdmin);

        AdminDto firstAdminDto = AdminDto.builder()
                .firstName("test")
                .lastName("test")
                .userName("test_1")
                .build();
        AdminDto secondAdminDto = AdminDto.builder()
                .firstName("test2")
                .lastName("test2")
                .userName("test_2")
                .build();

        expected = List.of(firstAdminDto, secondAdminDto);
    }

    @Test
    public void testFindAll() {
        when(adminJpaRepository.findAll()).thenReturn(admins);
        List<AdminDto> actual = adminService.findAll();
        assertEquals(expected, actual);
    }

}
