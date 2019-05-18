package com.magpie.entity;

import javax.persistence.*;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="ACCOUNT", nullable=false, length=50)
    private String account;

    @Column(name="NAME", nullable=false, length=100)
    private String name;

    @Column(name="PASWORD", nullable=false, length=100)
    private Integer password;

    @Column(name="COUNTRY_CODE", nullable=false, length=3)
    private String countryCode;
}
