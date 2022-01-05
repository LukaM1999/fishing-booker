package com.fishingbooker.model;

import com.fishingbooker.dto.RegistrationDTO;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin extends RegisteredUser{

	public Admin(){super();}

	public Admin(String username, String password, String name, String surname, String email, String address, String city, String country, String phone, String role) {
		super(username, password, name, surname, email, address, city, country, phone, role);
	}

	public Admin(RegistrationDTO dto){
		super(dto.getUsername(), dto.getPassword(), dto.getName(), dto.getSurname(), dto.getEmail(), dto.getAddress(), dto.getCity(), dto.getCountry(), dto.getPhone(), dto.getRole());
	}

}
