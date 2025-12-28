package com.codeshutlle.project.JWTAndOAuth2.controller;

import com.codeshutlle.project.JWTAndOAuth2.DTO.StudentDTO;
import com.codeshutlle.project.JWTAndOAuth2.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;

    @PostMapping
    public StudentDTO  createStudent(@RequestBody StudentDTO studentDTO){
        return studentService.createStudent(studentDTO);
    }
}
