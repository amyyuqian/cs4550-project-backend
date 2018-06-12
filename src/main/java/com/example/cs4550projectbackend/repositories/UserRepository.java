package com.example.cs4550projectbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.cs4550projectbackend.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	
}
