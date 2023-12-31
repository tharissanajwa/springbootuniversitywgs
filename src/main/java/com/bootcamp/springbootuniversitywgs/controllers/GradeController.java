package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.models.Grade;
import com.bootcamp.springbootuniversitywgs.services.GradeService;
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

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait nilai
@RestController
@RequestMapping("/grades")
public class GradeController {
    @Autowired
    private GradeService gradeService;

    // Metode untuk mengambil semua data nilai dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllGrade() {
        List<Grade> grades = gradeService.getAllGrade();
        ApiResponse response = new ApiResponse(gradeService.getResponseMessage(), grades);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data nilai berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getGradeById(@PathVariable("id") Long id) {
        Grade grades = gradeService.getGradeById(id);
        ApiResponse response = new ApiResponse(gradeService.getResponseMessage(), grades);
        if (grades != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk mengambil data nilai berdasarkan id student course dari fungsi yg telah dibuat di service
    @GetMapping("/student-courses/{studentCourse}")
    public ResponseEntity<ApiResponse> getGradeByStudentCourseId(@PathVariable("studentCourse") Long studentCourseId) {
        List<Grade> grades = gradeService.getGradeByStudentCourseId(studentCourseId);
        ApiResponse response = new ApiResponse(gradeService.getResponseMessage(), grades);
        if (grades != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat nilai baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertGrade(@RequestBody Grade grade) {
        Grade grades = gradeService.insertGrade(grade.getName(), grade.getGrade(), grade.getStudentCourse().getId());
        ApiResponse response = new ApiResponse(gradeService.getResponseMessage(), grades);
        if (grades != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk memperbarui informasi nilai dari fungsi yg telah dibuat di service
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateGrade(@PathVariable("id") Long id, @RequestBody Grade grade) {
        Grade grades = gradeService.updateGrade(id, grade.getName(), grade.getGrade(), grade.getStudentCourse().getId());
        ApiResponse response = new ApiResponse(gradeService.getResponseMessage(), grades);
        if (grades != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
