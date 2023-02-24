package com.example.shop.service.user;

import com.example.shop.dto.UserDto;
import com.example.shop.entities.Role;
import com.example.shop.entities.User;
import com.example.shop.repository.user.UserRepository;
import com.example.shop.service.admin.AdminService;
import com.example.shop.service.role.RoleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.webjars.NotFoundException;
import java.util.List;


@Slf4j
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleService roleService;
    private PasswordEncoder passwordEncoder;
    private AdminService adminService;

    @Transactional
    @Override
    public User create(User user) {
        Role admin = roleService.findByName("ROLE_ADMIN");
        Role roleUser = roleService.findByName("ROLE_USER");
        if (adminService.findAll().contains(adminService.toAdminDto(user))) {
            user.setRole(admin);
        } else {
            user.setRole(roleUser);
        }
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public User findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new NotFoundException
                        (String.format("Not found user with id: %s", id)));
    }

    @Override
    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Override
    public User update(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User findByUserName(String userName) {
        return userRepository.findByUserName(userName);
    }

    @Override
    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    @Override
    public User findByUserNameAndPassword(String userName, String password) {
        return userRepository.findByUserNameAndPassword(userName, password);
    }

    @Override
    public Boolean userNameUniqCheck(String userName) {
        return findByUserName(userName) == null;
    }

    @Override
    public User toUser(UserDto userDto) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .userName(userDto.getUserName())
                .password(userDto.getPassword())
                .build();
    }

    @Override
    public Page<User> findPage(int currentPage) {
        Pageable pageable = PageRequest.of(currentPage - 1, 5);
        return userRepository.findAll(pageable);
    }

}
