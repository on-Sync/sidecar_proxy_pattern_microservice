package com.putstack.cqrs_axon_common.events;

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
    private String address;
    private String ssn;
}
