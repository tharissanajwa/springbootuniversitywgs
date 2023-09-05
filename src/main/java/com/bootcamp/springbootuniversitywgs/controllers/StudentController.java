package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.models.Student;
import com.bootcamp.springbootuniversitywgs.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    @GetMapping
    private ResponseEntity<ApiResponse> getAllStudent() {
        List<Student> students = studentService.getAllStudent();
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    private ResponseEntity<ApiResponse> getStudentById(@PathVariable("id") Long id) {
        Student students = studentService.getStudentById(id);
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        if (students != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping
    private ResponseEntity<ApiResponse> insertStudent(@RequestBody Student student) {
        Student students = studentService.insertStudent(student.getName(), student.getMajor().getId());
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        if (students != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    private ResponseEntity<ApiResponse> updateStudent(@PathVariable("id") Long id, @RequestBody Student student) {
        Student students = studentService.updateStudent(id, student.getName(), student.getMajor().getId());
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        if (students != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    private ResponseEntity<ApiResponse> disableStudent(@PathVariable("id") Long id) {
        boolean valid = studentService.disableStudent(id);
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
