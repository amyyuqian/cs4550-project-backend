package com.example.cs4550projectbackend.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.cs4550projectbackend.models.User;
import com.example.cs4550projectbackend.repositories.UserRepository;

import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserService {
	@Autowired
	UserRepository repository;
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		repository.deleteById(id);
	}
	
	@PostMapping("/api/user")
	public User createUser(@RequestBody User user) {
		return repository.save(user);
	}
	
	@PostMapping("/api/login")
	public User login(@RequestBody User user, HttpSession session) {
		Optional<User> data = repository.findUserByCredentials(user.getUsername(), user.getPassword());
		if (data.isPresent()) {
			session.setAttribute("user", user.getUsername());
		}
		return data.get();
	}
	
	@GetMapping("/api/profile")
	public User getProfile(HttpSession session) {
		String username = (String) session.getAttribute("user");	
		Optional<User> data = repository.findUserByUsername(username);
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PutMapping("/api/profile/{id}")
	public User updateProfile(@PathVariable("id") int id, @RequestBody User body) {
		Optional<User> data = repository.findById(id);
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(body.getFirstName());
			user.setLastName(body.getLastName());
			repository.save(user);
			return user;
		}
		return null;
	}
	
	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	
	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return (List<User>) repository.findAll();
	}
	
	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User newUser) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			user.setFirstName(newUser.getFirstName());
			user.setUsername(newUser.getUsername());
			user.setLastName(newUser.getLastName());
			repository.save(user);
			return user;
		}
		return null;
	}
	
	@GetMapping("/api/username/{username}")
	public User findUserByUsername(@PathVariable("username")String username) {
		Optional<User> data = repository.findUserByUsername(username);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/register")
	public User register(@RequestBody User user, HttpSession session, HttpServletResponse response) {
		Optional<User> data = repository.findUserByUsername(user.getUsername());
		if (data.isPresent()) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return null;
		} else {
			session.setAttribute("user", user.getUsername());
			return repository.save(user);
		}
	}
	
	@GetMapping("/api/user/{userId}")
	public User findUserById(@PathVariable("userId") int userId) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/user/{userId}/follows")
	public Set<User> getFollowers(@PathVariable("userId") int userId) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			return data.get().getFollowers();
		}
		return null;
	}
	
	@GetMapping("/api/user/{userId}/following")
	public Set<User> getFollowing(@PathVariable("userId") int userId) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			return data.get().getFollowing();
		}
		return null;
	}
	
	@PutMapping("/api/user/{id1}/follow/{id2}")
	public User followUserById(@PathVariable("id1")int id1, @PathVariable("id2")int id2) {
		Optional<User> data1 = repository.findById(id1);
		Optional<User> data2 = repository.findById(id2);
		
		if (data1.isPresent() && data2.isPresent()) {
			User u1 = data1.get();
			User u2 = data2.get();
			u1.getFollowing().add(u2);
			u2.getFollowers().add(u1);
			return repository.save(u1);
		}
		return null;
	}
}
