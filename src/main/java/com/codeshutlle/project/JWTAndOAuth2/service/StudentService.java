package com.codeshutlle.project.JWTAndOAuth2.service;

import com.codeshutlle.project.JWTAndOAuth2.DTO.StudentDTO;
import com.codeshutlle.project.JWTAndOAuth2.entity.Student;
import com.codeshutlle.project.JWTAndOAuth2.repository.StudetRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentService {
    private final StudetRepository studetRepository;
    private final ModelMapper modelMapper;

    public StudentDTO createStudent(StudentDTO studentDTO){
        Student student = modelMapper.map(studentDTO,Student.class);
        Student saved=studetRepository.save(student);
        return modelMapper.map(saved,StudentDTO.class);
    }
}
