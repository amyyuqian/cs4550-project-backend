package com.example.cs4550projectbackend.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Image {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private int id;
	private String url;
	private String type;
	private int likes = 0;
	
	@OneToMany(mappedBy="image")
	@JsonIgnore
	private List<Comment> comments;
	
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = {
                CascadeType.PERSIST,
                CascadeType.MERGE
            },
            mappedBy = "favorites")
	@JsonIgnore
    private Set<User> favoritedBy = new HashSet<User>();
	
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public int getLikes() {
		return likes;
	}
	public void setLikes(int likes) {
		this.likes = likes;
	}
	public List<Comment> getComments() {
		return comments;
	}
	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}
	public Set<User> getFavoritedBy() {
		return favoritedBy;
	}
	public void setFavoritedBy(Set<User> favoritedBy) {
		this.favoritedBy = favoritedBy;
	}
 }
