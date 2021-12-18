package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;

@Entity
public class Admin extends PUser {
	@Column
	private String adminType;
	@Column
	private long works; // U kojoj apoteci je admin, ako je admin apoteke po tipu(po id), ako je 0 onda je SISTEMSKI admin!

	public String getAdminType() {
		return adminType;
	}

	public void setAdminType(String adminType) {
		this.adminType = adminType;
	}

	public long getWorks() {
		return works;
	}

	public void setWorks(long works) {
		this.works = works;
	}

	
	public Admin() {
		super();
	}

	public Admin(long id, String name, String surname, String email, String password, String address, String city,
                 String country, int number, String role, String adminType, String worksID, String authenticated, String verificationCode) {
		super(id, name, surname, email, password, address, city, country, number, role, worksID, authenticated, verificationCode);
	}

}
