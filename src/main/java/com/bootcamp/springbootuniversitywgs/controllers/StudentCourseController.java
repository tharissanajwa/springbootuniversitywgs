package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.models.StudentCourse;
import com.bootcamp.springbootuniversitywgs.services.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student-courses")
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllStudentCourse() {
        List<StudentCourse> studentCourses = studentCourseService.getAllStudentCourse();
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentCourseById(@PathVariable("id") Long id) {
        StudentCourse studentCourses = studentCourseService.getStudentCourseById(id);
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        if (studentCourses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> insertStudentCourse(@RequestBody StudentCourse studentCourse) {
        StudentCourse studentCourses = studentCourseService.insertStudentCourse(studentCourse.getStudent().getId(), studentCourse.getCourse().getId());
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        if (studentCourses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudentCourse(@PathVariable("id") Long id, @RequestBody StudentCourse studentCourse) {
        StudentCourse studentCourses = studentCourseService.updateStudentCourse(id, studentCourse.getStudent().getId(), studentCourse.getCourse().getId());
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        if (studentCourses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}