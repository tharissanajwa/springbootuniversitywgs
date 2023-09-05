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
        if (inputValidation(studentId, courseId) != "") {
            responseMessage = inputValidation(studentId, courseId);
        } else {
            newStudentCourse = new StudentCourse(studentService.getStudentById(studentId), courseService.getCourseById(courseId));
            studentCourseRepository.save(newStudentCourse);
            responseMessage = "Data successfully added!";
        }

        return newStudentCourse;
    }

    public StudentCourse updateStudentCourse(Long id, Long studentId, Long courseId) {
        StudentCourse studentCourse = null;
        if (getStudentCourseById(id) == null) {
            responseMessage = "Sorry, id student course is not found.";
        } else if (inputValidation(studentId, courseId) != "") {
            responseMessage = inputValidation(studentId, courseId);
        } else {
            getStudentCourseById(id).setStudent(studentService.getStudentById(studentId));
            getStudentCourseById(id).setCourse(courseService.getCourseById(courseId));
            studentCourse = getStudentCourseById(id);
            studentCourseRepository.save(studentCourse);
            responseMessage = "Data successfully updated!";
        }
        return studentCourse;
    }

    private String inputValidation(Long studentId, Long courseId) {
        String result = "";
        if (studentService.getStudentById(studentId) == null) {
            result = "Sorry, id student is not found!";
        } else if (courseService.getCourseById(courseId) == null) {
            result = "Sorry, id course is not found!";
        } else {
            // do nothing
        }
        return result;
    }
}
