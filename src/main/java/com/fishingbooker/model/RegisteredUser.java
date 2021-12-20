package com.fishingbooker.model;
import com.fishingbooker.model.enumeration.UserRole;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class RegisteredUser {
    @Id
    @Column
    private String Username;
    @Column
    private String Password;
    @Column
    private String Name;
    @Column
    private String Surname;
    @Column
    private String Email;
    @Column
    private String Address;
    @Column
    private String City;
    @Column
    private String Country;
    @Column
    private String Phone;
    @Column
    private UserRole Role;

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getSurname() {
        return Surname;
    }

    public void setSurname(String surname) {
        Surname = surname;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getCity() {
        return City;
    }

    public void setCity(String city) {
        City = city;
    }

    public String getCountry() {
        return Country;
    }

    public void setCountry(String country) {
        Country = country;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public UserRole getRole() {
        return Role;
    }

    public void setRole(UserRole role) {
        Role = role;
    }

    public RegisteredUser() {}

    public RegisteredUser(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, UserRole role){
        Username = username;
        Password = password;
        Name = name;
        Surname = surname;
        Email = email;
        Address = address;
        City = city;
        Country = country;
        Phone = phone;
        Role = role;
    }
}
