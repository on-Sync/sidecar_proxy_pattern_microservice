package com.putstack.cqrs_axon_common.user.events;

import com.putstack.cqrs_axon_common.user.entity.Membership;
import com.putstack.cqrs_axon_common.user.entity.UserAddress;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserCreationEvent {
    private String userId;
    private String email;
    private String password;
    private String name;
    private int age;
    private String ssn;
    private UserAddress address;
    private Membership membership;
}
