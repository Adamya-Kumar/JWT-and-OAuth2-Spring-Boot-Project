package com.codeshutlle.project.JWTAndOAuth2.repository;


import com.codeshutlle.project.JWTAndOAuth2.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudetRepository extends JpaRepository<Student,Long> {
}
