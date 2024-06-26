package com.studiesjpa.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.studiesjpa.jpa.models.Author;

public interface AuthorRepository extends JpaRepository<Author, Integer>{

}
