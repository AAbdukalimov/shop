package com.example.shop.service;

import com.example.shop.entities.Role;
import com.example.shop.repository.role.RoleRepository;
import com.example.shop.service.role.RoleServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RoleServiceImplTest {

    @Mock
    private RoleRepository roleRepository;
    @InjectMocks
    private RoleServiceImpl roleService;

    @Test
    public void testFindByName() {
        Role role = Role.builder()
                .id(1L)
                .name("ROLE_ADMIN")
                .build();

        when(roleRepository.findByName(role.getName())).thenReturn(role);
        Role actual = roleService.findByName(role.getName());

        assertEquals(role, actual);
    }

}
