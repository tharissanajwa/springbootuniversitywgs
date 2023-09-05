package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.Course;
import com.bootcamp.springbootuniversitywgs.repositories.CourseRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseService {
    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private Utility utility;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Course> getAllCourse() {
        if (courseRepository.findAllByIsDeletedFalse().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data course.";
        } else {
            responseMessage = null;
        }
        return courseRepository.findAllByIsDeletedFalse();
    }


    public Course getCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findByIdAndIsDeletedFalse(id);
        if (optionalCourse.isPresent()) {
            responseMessage = null;
            return optionalCourse.get();
        } else {
            responseMessage = "Sorry, id course is not found.";
            return null;
        }
    }

    public Course insertCourse(String name) {
        Course newCourse = null;
        int inputCheck = utility.inputCheck(utility.inputTrim(name)); // Fungsinya sebagai validasi dari nama yg diinputkan pengguna
        if (inputCheck == 1) {
            responseMessage = "Sorry, course name cannot be blank.";
        } else if (inputCheck == 2) {
            responseMessage = "Sorry, course name can only filled by letters";
        } else {
            newCourse = new Course(utility.inputTrim(name));
            courseRepository.save(newCourse);
            responseMessage = "Data successfully added!";
        }
        return newCourse;
    }

    public Course updateCourse(Long id, String name) {
        Course course = null;
        int inputCheck = utility.inputCheck(utility.inputTrim(name)); // Fungsinya sebagai validasi dari nama yg diinputkan pengguna
        if (inputCheck == 1) {
            responseMessage = "Sorry, course name cannot be blank.";
        } else if (inputCheck == 2) {
            responseMessage = "Sorry, course name can only filled by letters";
        } else {
            if (getCourseById(id) != null) {
                getCourseById(id).setName(utility.inputTrim(name));
                course = getCourseById(id);
                courseRepository.save(course);
                responseMessage = "Data successfully updated!";
            }
        }
        return course;
    }

    public boolean disableCourse(Long id) {
        boolean result = false;
        if (getCourseById(id) != null) {
            getCourseById(id).setDeleted(true);
            Course course = getCourseById(id);
            courseRepository.save(course);
            result = true;
            responseMessage = "Data deactivated successfully!";
        }
        return result;
    }
}
