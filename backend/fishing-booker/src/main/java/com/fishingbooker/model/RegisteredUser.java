package com.fishingbooker.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "username")
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class RegisteredUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    @Id
    @Column
    private String username;
    @Column
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String password;
    @Column
    private String name;
    @Column
    private String surname;
    @Column
    private String email;
    @Column
    private String address;
    @Column
    private String city;
    @Column
    private String country;
    @Column
    private String phone;

    @Column(name = "enabled")
    private boolean enabled;

    @Column(name = "last_password_reset_date")
    private Timestamp lastPasswordResetDate;

    @OneToOne(cascade = CascadeType.ALL)
    private Role role;

    @Column(columnDefinition="Decimal(3,2) default '0.00'")
    private double averageRating;

    @Column(columnDefinition = "Integer default 0")
    private int timesRated;

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<Role>(List.of(this.role));
    }

    @Override
    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public int getTimesRated() {
        return timesRated;
    }

    public void setTimesRated(int timesRated) {
        this.timesRated = timesRated;
    }

    public void setPassword(String password) {
        Timestamp now = new Timestamp(new Date().getTime());
        this.setLastPasswordResetDate(now);
        this.password = password;
    }

    public RegisteredUser() {
        super();
    }

    public RegisteredUser(String username, String password, String name, String surname, String email, String address,
                          String city, String country, String phone, String role) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.address = address;
        this.city = city;
        this.country = country;
        this.phone = phone;
        this.role = new Role(role);
        this.enabled = false;
        this.averageRating = 0;
        this.timesRated = 0;
    }

    public Timestamp getLastPasswordResetDate() {
        return lastPasswordResetDate;
    }

    public void setLastPasswordResetDate(Timestamp lastPasswordResetDate) {
        this.lastPasswordResetDate = lastPasswordResetDate;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }
}
