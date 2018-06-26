package com.example.cs4550projectbackend.services;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.annotation.*;

import com.example.cs4550projectbackend.models.Comment;
import com.example.cs4550projectbackend.models.Image;
import com.example.cs4550projectbackend.models.User;
import com.example.cs4550projectbackend.repositories.CommentRepository;
import com.example.cs4550projectbackend.repositories.ImageRepository;
import com.example.cs4550projectbackend.repositories.UserRepository;

@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
public class CommentService {
	@Autowired
	UserRepository userRepo;
	@Autowired
	ImageRepository imgRepo;
	@Autowired
	CommentRepository commentRepo;
	
	@PostMapping("/api/image/{iid}/comment")
	public Comment createComment(@PathVariable("iid") int iid, HttpServletRequest request,
			@RequestBody Comment body) {
		Optional<Image> imgData = imgRepo.findById(iid);
		String curUsername = (String) request.getServletContext().getAttribute("user");	
		Optional<User> curData = userRepo.findUserByUsername(curUsername);
		
		if (imgData.isPresent() && curData.isPresent()) {
			body.setImage(imgData.get());
			body.setUser(curData.get());
			return commentRepo.save(body);
		}
		return null;
	}
	
	@DeleteMapping("/api/comment/{id}")
	public void deleteComment(@PathVariable("id")int id) {
		commentRepo.deleteById(id);
	}
	
	@GetMapping("/api/image/{id}/comment")
	public List<Comment> getCommentsForImage(@PathVariable("id")int id) {
		Optional<Image> data = imgRepo.findById(id);
		
		if (data.isPresent()) {
			Image img = data.get();
			return img.getComments();
		}
		return null;
	}
}
