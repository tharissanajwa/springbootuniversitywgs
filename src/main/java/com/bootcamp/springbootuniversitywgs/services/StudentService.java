package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.Student;
import com.bootcamp.springbootuniversitywgs.repositories.StudentRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Kelas ini bertanggung jawab untuk mengelola data mahasiswa
@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private MajorService majorService;

    @Autowired
    private Utility utility;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar mahasiswa yang belum terhapus melalui repository
    public List<Student> getAllStudent() {
        if (studentRepository.findAllByIsDeletedFalse().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data student.";
        } else {
            responseMessage = null;
        }
        return studentRepository.findAllByIsDeletedFalse();
    }

    // Metode untuk mendapatkan data mahasiswa berdasarkan id melalui repository
    public Student getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findByIdAndIsDeletedFalse(id);
        if (optionalStudent.isPresent()) {
            responseMessage = null;
            return optionalStudent.get();
        }
        responseMessage = "Sorry, id student is not found.";
        return null;
    }

    // Metode untuk menambahkan mahasiswa baru ke dalam data melalui repository
    public Student insertStudent(String name, Long majorId) {
        Student newStudent = null;
        if (inputValidation(name, majorId) != "") {
            responseMessage = inputValidation(name, majorId);
        } else {
            newStudent = new Student(utility.inputTrim(name), majorService.getMajorById(majorId));
            studentRepository.save(newStudent);
            responseMessage = "Data successfully added!";
        }
        return newStudent;
    }

    // Metode untuk memperbarui informasi mahasiswa melalui repository
    public Student updateStudent(Long id, String name, Long majorId) {
        Student student = null;
        if (inputValidation(name, majorId) != "") {
            responseMessage = inputValidation(name, majorId);
        } else if (getStudentById(id) != null) {
            getStudentById(id).setName(utility.inputTrim(name));
            getStudentById(id).setMajor(majorService.getMajorById(majorId));
            student = getStudentById(id);
            studentRepository.save(student);
            responseMessage = "Data successfully updated!";
        }
        return student;
    }

    // Metode untuk menghapus data mahasiswa secara soft delete melalui repository
    public boolean disableStudent(Long id) {
        boolean result = false;
        if (getStudentById(id) != null) {
            getStudentById(id).setDeleted(true);
            Student student = getStudentById(id);
            studentRepository.save(student);
            result = true;
            responseMessage = "Data deactivated successfully!";
        }
        return result;
    }

    // Metode untuk memvalidasi inputan pengguna dan mengecek apakah id jurusan nya ada atau tidak
    private String inputValidation(String name, Long majorId) {
        String result = "";
        if (utility.inputCheck(utility.inputTrim(name)) == 1) {
            result = "Sorry, major name cannot be blank.";
        } else if (utility.inputCheck(utility.inputTrim(name)) == 2) {
            result = "Sorry, major name can only filled by letters";
        } else if (majorId == null) {
            result = "Sorry, id major is required.";
        } else if (majorService.getMajorById(majorId) == null) {
            result = "Sorry, id major is not found.";
        } else {
            // do nothing
        }
        return result;
    }
}
