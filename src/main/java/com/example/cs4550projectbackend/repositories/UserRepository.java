package com.example.cs4550projectbackend.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4550projectbackend.models.User;

public interface UserRepository extends CrudRepository<User, Integer> {
	@Query("SELECT u FROM User u WHERE u.username=:username AND u.password=:password")
	Optional<User> findUserByCredentials(
		@Param("username") String username, 
		@Param("password") String password);
	
	@Query("SELECT u FROM User u WHERE u.username=:username")
	Optional<User> findUserByUsername(
		@Param("username") String username);
	
	@Query("SELECT u FROM User u WHERE UPPER(u.username) LIKE CONCAT('%',UPPER(:username),'%')")
	List<User> findLikeUsers(@Param("username") String username);
}
