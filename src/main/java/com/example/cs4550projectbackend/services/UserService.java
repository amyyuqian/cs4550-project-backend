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

import com.example.cs4550projectbackend.models.Image;
import com.example.cs4550projectbackend.models.User;
import com.example.cs4550projectbackend.repositories.ImageRepository;
import com.example.cs4550projectbackend.repositories.UserRepository;

import java.util.*;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserService {
	@Autowired
	UserRepository repository;
	ImageRepository imgRepo;
	
	@DeleteMapping("/api/user/{userId}")
	public void deleteUser(@PathVariable("userId") int id) {
		repository.deleteById(id);
	}
	
	@PostMapping("/api/user")
	public User createUser(@RequestBody User user, HttpServletResponse response) {
		Optional<User> data = repository.findUserByUsername(user.getUsername());
		if (data.isPresent()) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return null;
		} else {
			return repository.save(user);
		}
	}
	
	@PostMapping("/api/login")
	public User login(@RequestBody User user, HttpSession session, HttpServletResponse response) {
		Optional<User> data = repository.findUserByCredentials(user.getUsername(), user.getPassword());
		if (data.isPresent()) {
			session.setAttribute("user", user.getUsername());
			return data.get();
		}
		response.setStatus(HttpServletResponse.SC_CONFLICT);
		return null;
	}
	
	@GetMapping("/api/profile")
	public User getProfile(HttpSession session, HttpServletResponse response) {
		String username = (String) session.getAttribute("user");	
		Optional<User> data = repository.findUserByUsername(username);
		if (data.isPresent()) {
			return data.get();
		}
		response.setStatus(HttpServletResponse.SC_CONFLICT);
		return null;
	}
	
	@PostMapping("/api/logout")
	public void logout(HttpSession session) {
		session.invalidate();
	}

	@GetMapping("/api/search/{username}")
	public List<User> searchUsers(@PathVariable("username") String username) {
		return repository.findLikeUsers(username);
	}
	
	@GetMapping("/api/user")
	public List<User> findAllUsers() {
		return (List<User>) repository.findAll();
	}
	
	@PutMapping("/api/user/{userId}")
	public User updateUser(@PathVariable("userId") int userId, @RequestBody User body) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			User user = data.get();
			user.setUsername(body.getUsername());
			user.setPassword(body.getPassword());
			user.setFirstName(body.getFirstName());
			user.setLastName(body.getLastName());
			user.setEmail(body.getEmail());
			user.setAdmin(body.isAdmin());
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
	
	@GetMapping("/api/user/{userId}/followers")
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
	
	@GetMapping("/api/user/{userId}/favorites")
	public Set<Image> getFavorites(@PathVariable("userId") int userId) {
		Optional<User> data = repository.findById(userId);
		if(data.isPresent()) {
			return data.get().getFavorites();
		}
		return null;
	}
	
	@GetMapping("/api/user/{userId}/isFollowing")
	public boolean isFollowing(@PathVariable("userId") int userId, HttpSession session) {
		Optional<User> data = repository.findById(userId);
		String curUsername = (String) session.getAttribute("user");	
		Optional<User> curData = repository.findUserByUsername(curUsername);
		if (curData.isPresent()) {
			User curUser = curData.get();
			Set<User> followers = data.get().getFollowers();
			if (followers.contains(curUser)) {
				return true;
			}
		}
		return false;
	}
	
	@GetMapping("/api/user/follow/{id}")
	public User followUserById(@PathVariable("id")int id, HttpSession session, HttpServletResponse res) {
		String curUsername = (String) session.getAttribute("user");	
		Optional<User> curData = repository.findUserByUsername(curUsername);
		Optional<User> data = repository.findById(id);
		if (curData.isPresent() && data.isPresent()) {
			User u1 = curData.get();
			User u2 = data.get();
			u1.getFollowing().add(u2);
			u2.getFollowers().add(u1);
			return repository.save(u1);
		}
		res.setStatus(HttpServletResponse.SC_NO_CONTENT);
		return null;
	}
	
	@PutMapping("/api/user/unfollow/{id}")
	public User unfollowUserById(@PathVariable("id")int id, HttpSession session, HttpServletResponse res) {
		String curUsername = (String) session.getAttribute("user");	
		Optional<User> curData = repository.findUserByUsername(curUsername);
		Optional<User> data = repository.findById(id);
		
		if (curData.isPresent() && data.isPresent()) {
			User u1 = curData.get();
			User u2 = data.get();
			u1.getFollowing().remove(u2);
			u2.getFollowers().remove(u1);
			return repository.save(u1);
		}
		res.setStatus(HttpServletResponse.SC_NO_CONTENT);
		return null;
	}
}
