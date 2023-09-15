package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.dto.requests.StudentRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.StudentResponse;
import com.bootcamp.springbootuniversitywgs.models.Student;
import com.bootcamp.springbootuniversitywgs.repositories.StudentRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

// Kelas ini bertanggung jawab untuk mengelola data mahasiswa
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MajorService majorService;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar mahasiswa yang belum terhapus melalui repository
    public Page<StudentResponse> getAllStudent(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Student> result = studentRepository.findAllByDeletedAtIsNullOrderByName(pageable);
        List<StudentResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (Student student : result.getContent()) {
                StudentResponse studentResponse = new StudentResponse(student);
                responses.add(studentResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data mahasiswa berdasarkan id melalui repository
    public Student getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findByIdAndDeletedAtIsNull(id);
        if (optionalStudent.isPresent()) {
            responseMessage = Utility.message("data_displayed");
            return optionalStudent.get();
        }
        responseMessage = Utility.message("student_not_found");
        return null;
    }

    // Metode untuk mendapatkan data mahasiswa melalui response berdasarkan id melalui repository
    public StudentResponse getStudentByIdResponse(Long id) {
        StudentResponse response = null;
        Student student = getStudentById(id);
        if (student != null) {
            response = new StudentResponse(student);
        }
        return response;
    }

    // Metode untuk menambahkan mahasiswa baru ke dalam data melalui repository
    public StudentResponse insertStudent(StudentRequest studentRequest) {
        StudentResponse response = null;
        Student result = new Student();
        String name = Utility.inputTrim(studentRequest.getName());
        Long majorId = studentRequest.getMajorId();
        if (!inputValidation(name, majorId).isEmpty()) {
            responseMessage = inputValidation(name, majorId);
        } else {
            result.setName(name);
            result.setMajor(majorService.getMajorById(majorId));
            studentRepository.save(result);
            response = new StudentResponse(result);
            responseMessage = Utility.message("data_added");
        }
        return response;
    }

    // Metode untuk memperbarui informasi mahasiswa melalui repository
    public StudentResponse updateStudent(Long id, StudentRequest studentRequest) {
        StudentResponse response = null;
        String name = Utility.inputTrim(studentRequest.getName());
        Long majorId = studentRequest.getMajorId();
        if (!inputValidation(name, majorId).isEmpty()) {
            responseMessage = inputValidation(name, majorId);
        } else if (getStudentById(id) != null) {
            Student student = getStudentById(id);
            student.setName(name);
            student.setMajor(majorService.getMajorById(majorId));
            studentRepository.save(student);
            response = new StudentResponse(student);
            responseMessage = Utility.message("data_updated");
        }
        return response;
    }

    // Metode untuk menghapus data mahasiswa secara soft delete melalui repository
    public boolean disableStudent(Long id) {
        boolean result = false;
        Student student = getStudentById(id);
        if (student != null) {
            student.setDeletedAt(new Date());
            studentRepository.save(student);
            result = true;
            responseMessage = Utility.message("data_deleted");
        }
        return result;
    }

    // Metode untuk memvalidasi inputan pengguna dan mengecek apakah id jurusan nya ada atau tidak
    private String inputValidation(String name, Long majorId) {
        String result = "";
        if (Utility.inputCheck(Utility.inputTrim(name)) == 1) {
            result = "Sorry, major name cannot be blank.";
        } else if (Utility.inputCheck(Utility.inputTrim(name)) == 2) {
            result = "Sorry, major name can only filled by letters";
        } else if (majorId == null) {
            result = "Sorry, id major is required.";
        } else if (majorService.getMajorById(majorId) == null) {
            result = Utility.message("major_not_found");
        }
        return result;
    }
}
