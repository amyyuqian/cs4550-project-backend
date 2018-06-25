package com.example.cs4550projectbackend.services;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cs4550projectbackend.models.Image;
import com.example.cs4550projectbackend.models.User;
import com.example.cs4550projectbackend.repositories.ImageRepository;
import com.example.cs4550projectbackend.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class ImageService {
	@Autowired
	ImageRepository imgRepo;
	UserRepository userRepo;

	@PostMapping("/api/image")
	public Image createImage(@RequestBody Image image, HttpServletResponse response) {
		Optional<Image> data = imgRepo.findImageByUrl(image.getUrl());
		if (data.isPresent()) {
			response.setStatus(HttpServletResponse.SC_CONFLICT);
			return null;
		}
		return imgRepo.save(image);
	}
	
	@PutMapping("/api/image/{id}")
	public Image updateImage(@PathVariable("id") int id, @RequestBody Image body) {
		Optional<Image> data = imgRepo.findById(id);
		
		if (data.isPresent()) {
			Image img = data.get();
			img.setLikes(body.getLikes());
			return imgRepo.save(img);
		}
		return null;
	}
	
	@GetMapping("/api/image")
	public List<Image> getAllImages() {
		return (List<Image>) imgRepo.findAll();
	}
	
	@GetMapping("/api/image/{id}")
	public Image findImageById(@PathVariable("id")int id) {
		Optional<Image> data = imgRepo.findById(id);
		
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@GetMapping("/api/imageUrl/{url}")
	public Image findImageByUrl(@PathVariable("url")String url) {
		Optional<Image> data = imgRepo.findImageByUrl(url);
		
		if (data.isPresent()) {
			return data.get();
		}
		return null;
	}
	
	@PostMapping("/api/image/{id}/like")
	public Image likeImage(@PathVariable("id")int id, HttpSession session) {
		Optional<Image> data = imgRepo.findById(id);
		
		if ( data.isPresent()) {
			Image img = data.get();
			img.setLikes(img.getLikes() + 1);
			return imgRepo.save(img);
		}
		return null;
	}
	
	@PostMapping("/api/image/{id}/unlike")
	public Image unlikeImage(@PathVariable("id")int id, HttpSession session) {
		Optional<Image> data = imgRepo.findById(id);
		
		if ( data.isPresent()) {
			Image img = data.get();
			img.setLikes(img.getLikes() - 1);
			return imgRepo.save(img);
		}
		return null;
	}
	
	@PostMapping("/api/image/{url}/favorite")
	public User addToFavorites(@PathVariable("url")String url, HttpSession session) {
		Optional<Image> data = imgRepo.findImageByUrl(url);
		String curUsername = (String) session.getAttribute("user");	
		Optional<User> curData = userRepo.findUserByUsername(curUsername);
		
		if (curData.isPresent() && data.isPresent()) {
			Image img = data.get();
			User user = curData.get();
			user.getFavorites().add(img);
			return userRepo.save(user);
		}
		return null;
	}
	
	@PostMapping("/api/image/{url}/unfavorite")
	public User removeFromFavorites(@PathVariable("url")String url, HttpSession session) {
		Optional<Image> data = imgRepo.findImageByUrl(url);
		String curUsername = (String) session.getAttribute("user");	
		Optional<User> curData = userRepo.findUserByUsername(curUsername);
		
		if (curData.isPresent() && data.isPresent()) {
			Image img = data.get();
			User user = curData.get();
			user.getFavorites().remove(img);
			return userRepo.save(user);
		}
		return null;
	}
	
	@GetMapping("/api/user/{userId}image/{url}/isInFavorites")
	public boolean isInFavorites(@PathVariable("url") String url, 
			@PathVariable("userID") int userId, HttpSession session) {
		Optional<Image> data = imgRepo.findImageByUrl(url);
		Optional<User> curData = userRepo.findById(userId);
		if (curData.isPresent() && data.isPresent()) {
			Image img = data.get();
			User user = curData.get();
			Set<Image> favorites = user.getFavorites();
			if (favorites.contains(img)) {
				return true;
			}
		}
		return false;
	}
	
}
