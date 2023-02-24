package com.example.shop.dto.authorize;

import lombok.Data;
import javax.validation.constraints.Pattern;

@Data
public class AuthRequest {

    @Pattern(regexp = "^[a-zA-Z\\d._-]{3,18}$",
            message = """
                    Username requirements:\s
                    Username consists of alphanumeric characters (a-zA-Z0-9), lowercase, or uppercase.
                    Username allowed of the dot (.), underscore (_), and hyphen (-).
                    The dot (.), underscore (_), or hyphen (-) must not be the first or last character.
                    The dot (.), underscore (_), or hyphen (-) does not appear consecutively, e.g., java..regex
                    The number of characters must be between 3 to 18.""")
    String userName;

    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$",
            message = """
                    Secure password requirements:\s
                    Password must contain at least one digit [0-9].
                    Password must contain at least one lowercase latin character [a-z].
                    Password must contain at least one uppercase latin character [A-Z].
                    Password must contain at least one special character like: ! @ # & ( )–{}:;',?/*~$^+=<>.
                    Password must contain a length of at least 8 characters and a maximum of 20 characters.""")
    String password;

}
