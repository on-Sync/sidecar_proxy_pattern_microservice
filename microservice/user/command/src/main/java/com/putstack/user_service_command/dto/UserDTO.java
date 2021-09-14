package com.putstack.user_service_command.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private int age;
    private String address;
    private String ssn;
}
