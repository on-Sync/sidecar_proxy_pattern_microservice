package com.putstack.user_service_query.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserSummary {
    @Id
    private String userId;
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    private String password;
    private String name;
    private int age;
    private String address;
    private String ssn;
    private int status;
}
