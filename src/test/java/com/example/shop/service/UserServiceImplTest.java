package com.example.shop.service;

import com.example.shop.dto.UserDto;
import com.example.shop.entities.User;
import com.example.shop.repository.user.UserRepository;
import com.example.shop.service.admin.AdminService;
import com.example.shop.service.role.RoleService;
import com.example.shop.service.user.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleService roleService;
    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private AdminService adminService;
    @Mock
    private Authentication authentication;
    @InjectMocks
    private UserServiceImpl userService;
    private User rawUser;
    private User firstUser;
    private List<User> users;

    @BeforeEach
    public void init() {
        rawUser = User.builder()
                .id(null)
                .firstName("test1")
                .lastName("test1")
                .userName("test_1")
                .password("Testtest$1")
                .build();

        firstUser = User.builder()
                .id(1L)
                .firstName("test1")
                .lastName("test1")
                .userName("test_1")
                .password("Testtest$1")
                .build();

        User secondUser = User.builder()
                .id(2L)
                .firstName("test2")
                .lastName("test2")
                .userName("test_2")
                .password("Testtest$2")
                .build();

        User thirdUser = User.builder()
                .id(2L)
                .firstName("test3")
                .lastName("test3")
                .userName("test_3")
                .password("Testtest$3")
                .build();

        users = List.of(firstUser, secondUser, thirdUser);
    }

    @Test
    public void testCreate() {
        when(userRepository.save(rawUser)).thenReturn(firstUser);
        User actual = userService.create(rawUser);
        assertEquals(firstUser, actual);
    }

    @Test
    void testFindById() {
        when(userRepository.findById(firstUser.getId())).thenReturn(Optional.of(firstUser));
        User actual = userService.findById(firstUser.getId());
        assertEquals(firstUser, actual);
    }

    @Test
    public void testFindAll() {
        when(userRepository.findAll()).thenReturn(users);
        List<User> actual = userService.findAll();
        assertEquals(users, actual);
    }

    @Test
    public void testUpdate() {
        User updatedUser = User.builder()
                .id(1L)
                .firstName("Test1")
                .lastName("Test1")
                .userName("Test_1")
                .password("Testtest$1")
                .build();

        when(userRepository.save(firstUser)).thenReturn(updatedUser);
        User actual = userService.update(firstUser);
        assertEquals(updatedUser, actual);
    }

    @Test
    public void testDeleteById() {
        userService.deleteById(firstUser.getId());
        verify(userRepository, times(1)).deleteById(anyLong());
        verify(userRepository, times(1)).deleteById(eq(firstUser.getId()));
    }

    @Test
    public void testFindByUserName() {
        when(userRepository.findByUserName(firstUser.getUserName())).thenReturn(firstUser);
        User actual = userService.findByUserName(firstUser.getUserName());
        assertEquals(firstUser, actual);
    }

    @Test
    public void testFindByUserNameAndPassword() {
        when(userRepository.findByUserNameAndPassword(firstUser.getUserName(), firstUser.getPassword())).thenReturn(firstUser);
        User actual = userService.findByUserNameAndPassword(firstUser.getUserName(), firstUser.getPassword());
        assertEquals(firstUser, actual);
    }

    @Test
    public void testGetCurrentUsername() {
        when(authentication.getName()).thenReturn(firstUser.getUserName());
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String expected = firstUser.getUserName();
        String actual = userService.getCurrentUsername();
        assertEquals(expected, actual);
    }

    @Test
    public void testUserNameUniqCheck() {
        when(userRepository.findByUserName(firstUser.getUserName())).thenReturn(firstUser);
        Boolean expected = false;
        Boolean actual = userService.userNameUniqCheck(firstUser.getUserName());
        assertEquals(expected, actual);
    }

    @Test
    public void testToUser() {
        UserDto userDto = UserDto.builder()
                .firstName("test1")
                .lastName("test1")
                .userName("test_1")
                .password("Testtest$1")
                .build();

        User actual = userService.toUser(userDto);
        if (actual.getFirstName().equals(rawUser.getFirstName())
                && actual.getId() == null && rawUser.getId() == null
                && actual.getLastName().equals(rawUser.getLastName())
                && actual.getUserName().equals(rawUser.getUserName())
                && actual.getPassword().equals(rawUser.getPassword())) {
            actual = rawUser;
        }
        assertEquals(rawUser, actual);
    }

}
