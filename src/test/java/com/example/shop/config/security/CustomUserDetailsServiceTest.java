package com.example.shop.config.security;

import com.example.shop.entities.Role;
import com.example.shop.entities.User;
import com.example.shop.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CustomUserDetailsServiceTest {
    @Mock
    private UserService userService;
    @InjectMocks
    private CustomUserDetailsService customUserDetailsService;

    @Test
    void testLoadUserByUsername() {
        String userName = "test";
        User user = User.builder()
                .userName(userName)
                .password("test")
                .role(Role.builder().name("ROLE_USER").build())
                .build();

        when(userService.findByUserName(anyString())).thenReturn(user);

        CustomUserDetails actual = customUserDetailsService.loadUserByUsername(userName);

        assertEquals(user.getUserName(), actual.getUsername());
        assertEquals(user.getPassword(), actual.getPassword());
        assertTrue(actual.getAuthorities().contains(new SimpleGrantedAuthority(user.getRole().getName())));
        verify(userService, times(1)).findByUserName(userName);
    }

}
