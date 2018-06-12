package com.example.cs4550projectbackend.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cs4550projectbackend.models.Image;
import com.example.cs4550projectbackend.models.User;
import com.example.cs4550projectbackend.repositories.ImageRepository;
import com.example.cs4550projectbackend.repositories.UserRepository;

@RestController
public class ImageService {
	@Autowired
	ImageRepository imgRepo;
	UserRepository userRepo;

	@PostMapping("/api/image")
	public Image createImage(@RequestBody Image image) {
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
}
