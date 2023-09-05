package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.StudentCourse;
import com.bootcamp.springbootuniversitywgs.repositories.StudentCourseRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentCourseService {
    @Autowired
    private StudentCourseRepository studentCourseRepository;

    @Autowired
    private StudentService studentService;

    @Autowired
    private CourseService courseService;

    @Autowired
    private Utility utility;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    public List<StudentCourse> getAllStudentCourse() {
        if (studentCourseRepository.findAll().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data student course.";
        } else {
            responseMessage = null;
        }
        return studentCourseRepository.findAll();
    }


    public StudentCourse getStudentCourseById(Long id) {
        Optional<StudentCourse> optionalStudentCourse = studentCourseRepository.findById(id);
        if (optionalStudentCourse.isPresent()) {
            responseMessage = null;
            return optionalStudentCourse.get();
        } else {
            responseMessage = "Sorry, id student course is not found.";
            return null;
        }
    }

    public StudentCourse insertStudentCourse(Long studentId, Long courseId) {
        StudentCourse newStudentCourse = null;

        if (studentService.getStudentById(studentId) != null) {
            if (courseService.getCourseById(courseId) != null) {
                newStudentCourse = new StudentCourse(studentService.getStudentById(studentId), courseService.getCourseById(courseId));
                studentCourseRepository.save(newStudentCourse);
                responseMessage = "Data successfully added!";
            } else {
                responseMessage = "Sorry, id course is not found!";
            }
        } else {
            responseMessage = "Sorry, id student is not found!";
        }
        return newStudentCourse;
    }

//    public StudentCourse updateStudentCourse(Long id, String name) {
//        StudentCourse studentCourse = null;
//        int inputCheck = utility.inputCheck(utility.inputTrim(name)); // Fungsinya sebagai validasi dari nama yg diinputkan pengguna
//        if (inputCheck == 1) {
//            responseMessage = "Sorry, studentCourse name cannot be blank.";
//        } else if (inputCheck == 2) {
//            responseMessage = "Sorry, studentCourse name can only filled by letters";
//        } else {
//            if (getStudentCourseById(id) != null) {
//                getStudentCourseById(id).setName(utility.inputTrim(name));
//                studentCourse = getStudentCourseById(id);
//                studentCourseRepository.save(studentCourse);
//                responseMessage = "Data successfully updated!";
//            }
//        }
//        return studentCourse;
//    }
}
