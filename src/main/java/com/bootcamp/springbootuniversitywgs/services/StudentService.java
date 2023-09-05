package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.Major;
import com.bootcamp.springbootuniversitywgs.models.Student;
import com.bootcamp.springbootuniversitywgs.repositories.StudentRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public List<Student> getAllStudent() {
        if (studentRepository.findAllByIsDeletedFalse().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data student.";
        } else {
            responseMessage = null;
        }
        return studentRepository.findAllByIsDeletedFalse();
    }


    public Student getStudentById(Long id) {
        Optional<Student> optionalStudent = studentRepository.findByIdAndIsDeletedFalse(id);
        if (optionalStudent.isPresent()) {
            responseMessage = null;
            return optionalStudent.get();
        } else {
            responseMessage = "Sorry, id student is not found.";
            return null;
        }
    }

    public Student insertStudent(String name, Long majorId) {
        Student newStudent = null;
        int inputCheck = utility.inputCheck(utility.inputTrim(name)); // Fungsinya sebagai validasi dari nama yg diinputkan pengguna
        if (inputCheck == 1) {
            responseMessage = "Sorry, student name cannot be blank.";
        } else if (inputCheck == 2) {
            responseMessage = "Sorry, student name can only filled by letters";
        } else {
            if (majorService.getMajorById(majorId) != null) {
                newStudent = new Student(utility.inputTrim(name), majorService.getMajorById(majorId));
                studentRepository.save(newStudent);
                responseMessage = "Data successfully added!";
            } else {
                responseMessage = "Sorry, id major is not found.";
            }
        }
        return newStudent;
    }

    public Student updateStudent(Long id, String name, Long majorId) {
        Student student = null;
        int inputCheck = utility.inputCheck(utility.inputTrim(name)); // Fungsinya sebagai validasi dari nama yg diinputkan pengguna
        if (inputCheck == 1) {
            responseMessage = "Sorry, student name cannot be blank.";
        } else if (inputCheck == 2) {
            responseMessage = "Sorry, student name can only filled by letters";
        } else {
            if (getStudentById(id) != null) {
                if (majorService.getMajorById(majorId) != null) {
                    getStudentById(id).setName(utility.inputTrim(name));
                    getStudentById(id).setMajor(majorService.getMajorById(majorId));
                    student = getStudentById(id);
                    studentRepository.save(student);
                    responseMessage = "Data successfully updated!";
                } else {
                    responseMessage = "Sorry, id major is not found.";
                }
            }
        }
        return student;
    }

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
}
