package com.fishingbooker.model;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class PUser {
	@Id
	@SequenceGenerator(name = "myUserSeqGen", sequenceName = "myUserSeq", initialValue = 1, allocationSize = 1)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "myUserSeqGen")
	@Column(name="id", unique=true, nullable=false)
	protected long id;
	@Column
	protected String name;
	@Column
	protected String surname;
	@Column(nullable = false, unique = true)
	protected String email;
	@Column
	protected String password;
	@Column
	protected String address;
	@Column
	protected String city;
	@Column
	protected String country;
	@Column
	protected int number;
	@Column
	protected String role; // Uloge za oznacavanje tipa korisnika: 
	@Column
	protected String worksID;
	@Column
	protected String authenticated;
	@Column
	protected String verificationCode;
	@Column
	protected Boolean firstLogin;
	
	
	public PUser() {
		super();
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", name=" + name + ", surname=" + surname + ", email=" + email + ", password=" + password
				+ ", address=" + address + ", city=" + city + ", country=" + country + ", number=" + number + ", role="
				+ role + "]";
	}

	public long getId() {
		return id;
	}
	
	public String getVerificationCode() {
		return verificationCode;
	}

	public void setVerificationCode(String verificationCode) {
		this.verificationCode = verificationCode;
	}

	public String getAuthenticated() {
		return authenticated;
	}

	public void setAuthenticated(String authenticated) {
		this.authenticated = authenticated;
	}

	public void setId(long id) {
		this.id = id;
	}
	
	public String getWorksID() {
		return worksID;
	}

	public void setWorksID(String works) {
		this.worksID = works;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public int getNumber() {
		return number;
	}

	public void setNumber(int number) {
		this.number = number;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	

	public Boolean getFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(Boolean firstLogin) {
		this.firstLogin = firstLogin;
	}

	public PUser(long id, String name, String surname, String email, String password, String address, String city, String country,
                 int number, String role, String worksID, String authenticated, String verificationCode) {
		super();
		this.id = id;
		this.name = name;
		this.surname = surname;
		this.email = email;
		this.password = password;
		this.address = address;
		this.city = city;
		this.country = country;
		this.number = number;
		this.role = role;
		this.worksID = worksID;
		this.authenticated = authenticated;
		this.verificationCode = verificationCode;
	}
	
	
	
}
