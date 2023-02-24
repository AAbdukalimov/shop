package com.example.shop.restcontrollers;

import com.example.shop.dto.UserDto;
import com.example.shop.entities.User;
import com.example.shop.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@Tag(name = "Users REST controller", description = "Users API")
@RequestMapping("/rest/users")
@RestController
@AllArgsConstructor
public class UsersRestController {

    private final UserService userService;

    @PostMapping()
    @Operation(summary = "Create user")
    @ResponseStatus(HttpStatus.CREATED)
    public User create(@Valid @RequestBody UserDto userDto) {
        return userService.create(userService.toUser(userDto));
    }

    @GetMapping()
    @Operation(summary = "Find all users")
    @ResponseStatus(HttpStatus.OK)
    public List<User> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    @Operation(summary = "Find user by id")
    @ResponseStatus(HttpStatus.OK)
    public User findById(@PathVariable Long id) {
        return userService.findById(id);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete user by id")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable(value = "id") Long id) {
        userService.deleteById(id);
    }

    @PatchMapping()
    @Operation(summary = "Update user")
    @ResponseStatus(HttpStatus.OK)
    public User update(@Valid @RequestBody User user) {
        return userService.update(user);
    }

    @GetMapping("/findByUserName")
    @Operation(summary = "Find user by username")
    @ResponseStatus(HttpStatus.OK)
    public User findByUserName(String userName) {
        return userService.findByUserName(userName);
    }

    @GetMapping("/getCurrentUserName")
    @Operation(summary = "Find current username")
    @ResponseStatus(HttpStatus.OK)
    public String getCurrentUserName() {
        return userService.getCurrentUsername();
    }

    @GetMapping("/findByUserNameAndPassword")
    @Operation(summary = "Find by username and password")
    @ResponseStatus(HttpStatus.OK)
    public User findByUserNameAndPassword(String userName, String password) {
        return userService.findByUserNameAndPassword(userName, password);
    }

    @GetMapping("/userNameUniqCheck")
    @Operation(summary = "Check username for uniqueness")
    @ResponseStatus(HttpStatus.OK)
    public Boolean userNameUniqCheck(String userName) {
        return userService.userNameUniqCheck(userName);
    }

}
