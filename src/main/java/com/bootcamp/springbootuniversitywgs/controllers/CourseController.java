package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.models.Course;
import com.bootcamp.springbootuniversitywgs.services.CourseService;
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
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllCourse() {
        List<Course> courses = courseService.getAllCourse();
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable("id") Long id) {
        Course courses = courseService.getCourseById(id);
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        if (courses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping
    public ResponseEntity<ApiResponse> insertCourse(@RequestBody Course course) {
        Course courses = courseService.insertCourse(course.getName());
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        if (courses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable("id") Long id, @RequestBody Course course) {
        Course courses = courseService.updateCourse(id, course.getName());
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        if (courses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> disableCourse(@PathVariable("id") Long id) {
        boolean valid = courseService.disableCourse(id);
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
