package com.bootcamp.springbootuniversitywgs.controllers;

import com.bootcamp.springbootuniversitywgs.models.ApiResponse;
import com.bootcamp.springbootuniversitywgs.models.Major;
import com.bootcamp.springbootuniversitywgs.services.MajorService;
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

// Kelas ini bertindak sebagai controller untuk mengatur permintaan terkait jurusan
@RestController
@RequestMapping("/majors")
public class MajorController {
    @Autowired
    private MajorService majorService;

    // Metode untuk mengambil semua data jurusan dari fungsi yg telah dibuat di service
    @GetMapping
    public ResponseEntity<ApiResponse> getAllMajor() {
        List<Major> majors = majorService.getAllMajor();
        ApiResponse response = new ApiResponse(majorService.getResponseMessage(), majors);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Metode untuk mengambil data jurusan berdasarkan id dari fungsi yg telah dibuat di service
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getMajorById(@PathVariable("id") Long id) {
        Major majors = majorService.getMajorById(id);
        ApiResponse response = new ApiResponse(majorService.getResponseMessage(), majors);
        if (majors != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk membuat jurusan baru dari fungsi yg telah dibuat di service
    @PostMapping
    public ResponseEntity<ApiResponse> insertMajor(@RequestBody Major major) {
        Major majors = majorService.insertMajor(major.getName());
        ApiResponse response = new ApiResponse(majorService.getResponseMessage(), majors);
        if (majors != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk memperbarui informasi jurusan dari fungsi yg telah dibuat di service
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> updateMajor(@PathVariable("id") Long id, @RequestBody Major major) {
        Major majors = majorService.updateMajor(id, major.getName());
        ApiResponse response = new ApiResponse(majorService.getResponseMessage(), majors);
        if (majors != null) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    // Metode untuk menghapus jurusan berdasarkan id dari fungsi yg telah dibuat di service
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> disableMajor(@PathVariable("id") Long id) {
        boolean valid = majorService.disableMajor(id);
        ApiResponse response = new ApiResponse(majorService.getResponseMessage(), null);
        if (valid) {
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }
}
