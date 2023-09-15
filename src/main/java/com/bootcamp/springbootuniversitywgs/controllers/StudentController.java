package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.dto.requests.StudentRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.StudentResponse;
import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.services.StudentService;
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

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait mahasiswa
@RestController
@RequestMapping("/students")
public class StudentController {
    @Autowired
    private StudentService studentService;

    // Metode untuk mengambil semua data mahasiswa dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllStudent(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        Page<StudentResponse> students = studentService.getAllStudent(page, limit);
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data mahasiswa berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getStudentById(@PathVariable("id") Long id) {
        StudentResponse students = studentService.getStudentByIdResponse(id);
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        if (students != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat mahasiswa baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertStudent(@RequestBody StudentRequest studentRequest) {
        StudentResponse students = studentService.insertStudent(studentRequest);
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        if (students != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk memperbarui informasi mahasiswa dari fungsi yg telah dibuat di service
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateStudent(@PathVariable("id") Long id, @RequestBody StudentRequest studentRequest) {
        StudentResponse students = studentService.updateStudent(id, studentRequest);
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), students);
        if (students != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menghapus mahasiswa berdasarkan id dari fungsi yg telah dibuat di service
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> disableStudent(@PathVariable("id") Long id) {
        boolean valid = studentService.disableStudent(id);
        ApiResponse response = new ApiResponse(studentService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
