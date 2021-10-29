package com.putstack.cqrs_axon_common.user.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDetail {
    @Id
    private String userId;
    @Column(length = 50, nullable = false, unique = true)
    private String email;
    private String password;
    private String name;
    private int age;
    private String ssn;
    private String phone;
    
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    UserAddress address;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, optional = false)
    Membership membership;
}
