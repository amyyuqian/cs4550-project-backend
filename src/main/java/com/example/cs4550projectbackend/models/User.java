package com.example.cs4550projectbackend.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
	private String email;
	private boolean isAdmin;
	
	@ManyToMany(cascade={CascadeType.ALL})
	@JoinTable(name="followers_following",
		joinColumns={@JoinColumn(name="follower_id")},
		inverseJoinColumns={@JoinColumn(name="following_id")})
	@JsonIgnore
	private Set<User> following = new HashSet<User>();
	
	@ManyToMany(mappedBy="following")
	@JsonIgnore
	private Set<User> followers = new HashSet<User>();
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                    CascadeType.PERSIST,
                    CascadeType.MERGE
                })
	@JoinTable(name="user_favorites",
		joinColumns={@JoinColumn(name="user_id")},
		inverseJoinColumns={@JoinColumn(name="image_id")})
	@JsonIgnore
	private Set<Image> favorites;
	
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
	public Set<User> getFollowing() {
		return following;
	}
	public void setFollowing(Set<User> following) {
		this.following = following;
	}
	public Set<User> getFollowers() {
		return followers;
	}
	public void setFollowers(Set<User> followers) {
		this.followers = followers;
	}
	public Set<Image> getFavorites() {
		return favorites;
	}
	public void setFavorites(Set<Image> favorites) {
		this.favorites = favorites;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public boolean getIsAdmin() {
		return isAdmin;
	}
	public void setIsAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}
	
}

