package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.dto.requests.StudentCourseRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.StudentCourseResponse;
import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.services.StudentCourseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait mahasiswa memilih matkul
@RestController
@RequestMapping("/student-courses")
public class StudentCourseController {
    @Autowired
    private StudentCourseService studentCourseService;

    // Metode untuk mengambil semua data mahasiswa memilih matkul dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllStudentCourse(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Page<StudentCourseResponse> studentCourses = studentCourseService.getAllStudentCourse(page, limit);
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data mahasiswa memilih matkul berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentCourseById(@PathVariable("id") Long id) {
        StudentCourseResponse studentCourses = studentCourseService.getStudentCourseByIdResponse(id);
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        if (studentCourses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk mengambil data mahasiswa memilih matkul berdasarkan id mahasiswa dari fungsi yg telah dibuat di service
    @GetMapping("/students/{student}")
    public ResponseEntity<ApiResponse> getStudentCourseByStudentId(@RequestParam("page") int page, @RequestParam("limit") int limit, @PathVariable("student") Long studentId) {
        Page<StudentCourseResponse> studentCourses = studentCourseService.getStudentCourseByStudentId(page, limit, studentId);
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        if (studentCourses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat data mahasiswa memilih matkul baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertStudentCourse(@RequestBody StudentCourseRequest studentCourseRequest) {
        StudentCourseResponse studentCourses = studentCourseService.insertStudentCourse(studentCourseRequest);
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        if (studentCourses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk memperbarui informasi mahasiswa memilih dari fungsi yg telah dibuat di service
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudentCourse(@PathVariable("id") Long id, @RequestBody StudentCourseRequest studentCourseRequest) {
        StudentCourseResponse studentCourses = studentCourseService.updateStudentCourse(id, studentCourseRequest);
        ApiResponse response = new ApiResponse(studentCourseService.getResponseMessage(), studentCourses);
        if (studentCourses != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
