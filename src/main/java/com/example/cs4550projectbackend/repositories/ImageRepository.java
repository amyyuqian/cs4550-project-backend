package com.example.cs4550projectbackend.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.example.cs4550projectbackend.models.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {
	@Query("SELECT i FROM Image i WHERE i.url=:url")
	Optional<Image> findImageByUrl(@Param("url")String url);
}
