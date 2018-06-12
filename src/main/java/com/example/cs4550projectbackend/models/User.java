package com.example.cs4550projectbackend.models;

import java.util.List;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<User> following;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<User> followers;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Image> favorites;
	@OneToMany(mappedBy="user")
	@JsonIgnore
	private List<Comment> comments;

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}

