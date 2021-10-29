package com.putstack.cqrs_axon_common.user.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserAddress {
    @Id
    private String zipCode;
    private String normal;
    private String detail;
    
    @OneToOne(mappedBy = "address")
    private UserDetail user;
}
