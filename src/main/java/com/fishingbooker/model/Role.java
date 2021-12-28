package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.io.Serializable;

// POJO koji implementira Spring Security GrantedAuthority kojim se mogu definisati role u aplikaciji

@Entity
@Table(name="ROLE")
public class Role implements GrantedAuthority, Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name="roleName")
    private String roleName;

    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @JsonIgnore
    @Override
    public String getAuthority() {
        return roleName;
    }

    public void setName(String name) {
        this.roleName = name;
    }

    public Role(String name) {
        this.roleName = name;
    }

    public Role(){}

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}

