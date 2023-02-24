package com.example.shop.service.user;

import com.example.shop.dto.UserDto;
import com.example.shop.entities.User;
import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    User create(User user);
    User findById(Long id);
    List<User> findAll();
    User update(User user);
    void deleteById(Long id);
    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);
    String getCurrentUsername();
    Boolean userNameUniqCheck(String userName);
    User toUser(UserDto userDto);
    Page<User> findPage(int currentPage);

}
