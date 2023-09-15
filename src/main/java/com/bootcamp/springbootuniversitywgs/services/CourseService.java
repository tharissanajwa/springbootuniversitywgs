package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.dto.requests.CourseRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.CourseResponse;
import com.bootcamp.springbootuniversitywgs.models.Course;
import com.bootcamp.springbootuniversitywgs.repositories.CourseRepository;
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
    public Page<CourseResponse> getAllCourse(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Course> result = courseRepository.findAllByDeletedAtIsNullOrderByName(pageable);
        List<CourseResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data course.";
        } else {
            for (Course course : result.getContent()) {
                CourseResponse courseResponse = new CourseResponse(course);
                responses.add(courseResponse);
            }
            responseMessage = "Data successfully displayed.";
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data matkul berdasarkan id melalui repository
    public Course getCourseById(Long id) {
        Optional<Course> optionalCourse = courseRepository.findByIdAndDeletedAtIsNull(id);
        if (optionalCourse.isPresent()) {
            responseMessage = "Data successfully displayed.";
            return optionalCourse.get();
        }
        responseMessage = "Sorry, id course is not found.";
        return null;
    }

    // Metode untuk mendapatkan data matkul melalui response berdasarkan id melalui repository
    public CourseResponse getCourseByIdResponse(Long id) {
        CourseResponse response = null;
        Course course = getCourseById(id);
        if (course != null) {
            response = new CourseResponse(course);
        }
        return response;
    }

    // Metode untuk menambahkan matkul baru ke dalam data melalui repository
    public CourseResponse insertCourse(CourseRequest courseRequest) {
        CourseResponse response = null;
        Course result = new Course();
        String name = utility.inputTrim(courseRequest.getName());
        if (!inputValidation(name).isEmpty()) {
            responseMessage = inputValidation(name);
        } else {
            result.setName(name);
            courseRepository.save(result);
            response = new CourseResponse(result);
            responseMessage = "Data successfully added!";
        }
        return response;
    }

    // Metode untuk memperbarui informasi matkul melalui repository
    public CourseResponse updateCourse(Long id, CourseRequest courseRequest) {
        CourseResponse response = null;
        String name = utility.inputTrim(courseRequest.getName());
        if (!inputValidation(name).isEmpty()) {
            responseMessage = inputValidation(name);
        } else if (getCourseById(id) != null) {
            Course course = getCourseById(id);
            course.setName(name);
            courseRepository.save(course);
            response = new CourseResponse(course);
            responseMessage = "Data successfully updated!";
        }
        return response;
    }

    // Metode untuk menghapus data matkul secara soft delete melalui repository
    public boolean disableCourse(Long id) {
        boolean result = false;
        Course course = getCourseById(id);
        if (course != null) {
            course.setDeletedAt(new Date());
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
        }
        return result;
    }
}
