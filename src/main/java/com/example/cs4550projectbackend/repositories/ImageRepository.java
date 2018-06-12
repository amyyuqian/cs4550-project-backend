package com.example.cs4550projectbackend.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.cs4550projectbackend.models.Image;

public interface ImageRepository extends CrudRepository<Image, Integer> {

}
