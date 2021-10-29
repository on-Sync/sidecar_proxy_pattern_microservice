package com.putstack.cqrs_axon_common.user.entity;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.MapsId;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonCreator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Membership {
    @Id
    private String membershipId;
    @Enumerated(EnumType.STRING)
    MembershipType level;
    
    @OneToOne(mappedBy = "membership")
    private UserDetail user;
}

enum MembershipType {
    NORMAL, PREMIUM;

    @JsonCreator
    static MembershipType from(String value) {
        return MembershipType.valueOf(value.toUpperCase());
    }
}
