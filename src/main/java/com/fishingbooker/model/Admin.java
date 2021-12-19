package com.fishingbooker.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Admin{
	@Id
	@Column
	public long Id;
	@Column
	public String Name;
	@Column
	public String Surname;

	public Admin() {}

	public Admin(long id, String name, String surname){
		Id = id;
		Name = name;
		Surname = surname;
	}

}
