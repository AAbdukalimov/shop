package com.example.shop.repository.user;

import com.example.shop.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    User findByUserName(String userName);
    User findByUserNameAndPassword(String userName, String password);

}
