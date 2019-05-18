package com.magpie.entity;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter(AccessLevel.PUBLIC)
@Setter(AccessLevel.PRIVATE)
public class Country {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name="CODE", nullable=false, length=3)
    private String code;

    @Column(name="NANE", nullable=false, length=50)
    private String name;

    @Column(name="LANG_CODE", nullable=false, length=50)
    private String langCode;

}
