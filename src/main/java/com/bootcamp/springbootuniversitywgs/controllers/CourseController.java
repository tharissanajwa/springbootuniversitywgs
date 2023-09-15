package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.dto.requests.CourseRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.CourseResponse;
import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.services.CourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait mata kuliah
@RestController
@RequestMapping("/courses")
public class CourseController {
    @Autowired
    private CourseService courseService;

    // Metode untuk mengambil semua data matkul dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllCourse(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Page<CourseResponse> courses = courseService.getAllCourse(page, limit);
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data matkul berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getCourseById(@PathVariable("id") Long id) {
        CourseResponse courses = courseService.getCourseByIdResponse(id);
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        if (courses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat matkul baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertCourse(@RequestBody CourseRequest courseRequest) {
        CourseResponse courses = courseService.insertCourse(courseRequest);
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        if (courses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk memperbarui informasi matkul dari fungsi yg telah dibuat di service
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateCourse(@PathVariable("id") Long id, @RequestBody CourseRequest courseRequest) {
        CourseResponse courses = courseService.updateCourse(id, courseRequest);
        ApiResponse response = new ApiResponse(courseService.getResponseMessage(), courses);
        if (courses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menghapus matkul berdasarkan dari fungsi yg telah dibuat di service
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
