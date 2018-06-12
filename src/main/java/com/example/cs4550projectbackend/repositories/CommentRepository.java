package com.example.cs4550projectbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.cs4550projectbackend.models.Comment;

public interface CommentRepository extends CrudRepository<Comment, Integer> {

}
