package com.putstack.cqrs_axon_common.user.dto;

import com.putstack.cqrs_axon_common.user.entity.Membership;
import com.putstack.cqrs_axon_common.user.entity.UserAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {
    private String email;
    private String password;
    private String name;
    private int age;
    private String ssn;
    private UserAddress address;
    private Membership membership;
}
