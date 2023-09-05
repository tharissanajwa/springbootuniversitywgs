package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.Course;
import com.bootcamp.springbootuniversitywgs.repositories.CourseRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// Kelas ini bertanggung jawab untuk mengelola data matkul
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

    // Metode untuk mendapatkan semua daftar matkul yang belum terhapus melalui repository
    public List<Course> getAllCourse() {
        if (courseRepository.findAllByIsDeletedFalse().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data course.";
        } else {
            responseMessage = null;
        }
        return courseRepository.findAllByIsDeletedFalse();
    }

    // Metode untuk mendapatkan data matkul berdasarkan id melalui repository
    public Course getCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findByIdAndIsDeletedFalse(id);
        if (optionalCourse.isPresent()) {
            responseMessage = null;
            return optionalCourse.get();
        }
        responseMessage = "Sorry, id course is not found.";
        return null;
    }

    // Metode untuk menambahkan matkul baru ke dalam data melalui repository
    public Course insertCourse(String name) {
        Course newCourse = null;
        if (inputValidation(name) != "") {
            responseMessage = inputValidation(name);
        } else {
            newCourse = new Course(utility.inputTrim(name));
            courseRepository.save(newCourse);
            responseMessage = "Data successfully added!";
        }
        return newCourse;
    }

    // Metode untuk memperbarui informasi matkul melalui repository
    public Course updateCourse(Long id, String name) {
        Course course = null;
        if (inputValidation(name) != "") {
            responseMessage = inputValidation(name);
        } else if (getCourseById(id) != null) {
            getCourseById(id).setName(utility.inputTrim(name));
            course = getCourseById(id);
            courseRepository.save(course);
            responseMessage = "Data successfully updated!";
        }
        return course;
    }

    // Metode untuk menghapus data matkul secara soft delete melalui repository
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

    // Metode untuk memvalidasi inputan pengguna
    private String inputValidation(String name) {
        String result = "";
        if (utility.inputContainsNumber(utility.inputTrim(name)) == 1) {
            result = "Sorry, course name cannot be blank.";
        } else if (utility.inputContainsNumber(utility.inputTrim(name)) == 2) {
            result = "Sorry, course name can only filled by letters and numbers";
        } else {
            // do nothing
        }
        return result;
    }
}
