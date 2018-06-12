package com.example.cs4550projectbackend.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.cs4550projectbackend.models.Comment;
import com.example.cs4550projectbackend.models.Image;
import com.example.cs4550projectbackend.models.User;
import com.example.cs4550projectbackend.repositories.CommentRepository;
import com.example.cs4550projectbackend.repositories.ImageRepository;
import com.example.cs4550projectbackend.repositories.UserRepository;

@RestController
public class CommentService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	ImageRepository imgRepo;
	@Autowired
	CommentRepository commentRepo;
	
	@PostMapping("/api/user/{uid}/image/{iid}/comment")
	public Comment createComment(@PathVariable("iid") int iid, @PathVariable("uid") int uid,
			@RequestBody Comment body) {
		Optional<Image> imgData = imgRepo.findById(iid);
		Optional<User> userData = userRepo.findById(uid);
		
		if (imgData.isPresent() && userData.isPresent()) {
			body.setImage(imgData.get());
			body.setUser(userData.get());
			return commentRepo.save(body);
		}
		return null;
	}
	
	@DeleteMapping("/api/comment/{id}")
	public void deleteComment(@PathVariable("id")int id) {
		commentRepo.deleteById(id);
	}
}
