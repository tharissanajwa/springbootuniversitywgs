package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.dto.requests.StudentCourseRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.StudentCourseResponse;
import com.bootcamp.springbootuniversitywgs.models.StudentCourse;
import com.bootcamp.springbootuniversitywgs.repositories.StudentCourseRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// Kelas ini bertanggung jawab untuk mengelola data mahasiswa memilih matkul
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

    // Metode untuk mendapatkan semua daftar mahasiswa memilih matkul melalui repository
    public Page<StudentCourseResponse> getAllStudentCourse(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<StudentCourse> result = studentCourseRepository.findAll(pageable);
        List<StudentCourseResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (StudentCourse studentCourse : result.getContent()) {
                StudentCourseResponse studentCourseResponse = new StudentCourseResponse(studentCourse);
                responses.add(studentCourseResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data mahasiswa memilih matkul berdasarkan id melalui repository
    public StudentCourse getStudentCourseById(Long id) {
        Optional<StudentCourse> optionalStudentCourse = studentCourseRepository.findById(id);
        if (optionalStudentCourse.isPresent()) {
            responseMessage = Utility.message("data_displayed");
            return optionalStudentCourse.get();
        }
        responseMessage = Utility.message("student_course_not_found");
        return null;
    }

    // Metode untuk mendapatkan data mahasiswa melalui response memilih matkul berdasarkan id melalui repository
    public StudentCourseResponse getStudentCourseByIdResponse(Long id) {
        StudentCourseResponse response = null;
        StudentCourse studentCourse = getStudentCourseById(id);
        if (studentCourse != null) {
            response = new StudentCourseResponse(studentCourse);
        }
        return response;
    }

    // Metode untuk mendapatkan data mahasiswa memilih matkul berdasarkan id mahasiswa melalui repository
    public Page<StudentCourseResponse> getStudentCourseByStudentId(int page, int limit, Long studentId) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<StudentCourse> studentCourses = studentCourseRepository.findByStudentIdOrderByStudentId(pageable, studentId);
        List<StudentCourseResponse> responses = new ArrayList<>();
        if (!studentCourses.isEmpty()) {
            for (StudentCourse studentCourse : studentCourses.getContent()) {
                StudentCourseResponse studentCourseResponse = new StudentCourseResponse(studentCourse);
                responses.add(studentCourseResponse);
            }
            responseMessage = Utility.message("data_displayed");
            return new PageImpl<>(responses, PageRequest.of(page, limit), studentCourses.getTotalElements());
        }
        Utility.message("student_not_found");
        return null;
    }

    // Metode untuk menambahkan data mahasiswa memilih matkul baru ke database melalui repository
    public StudentCourseResponse insertStudentCourse(StudentCourseRequest studentCourseRequest) {
        StudentCourseResponse response = null;
        StudentCourse result = new StudentCourse();
        Long studentId = studentCourseRequest.getStudentId();
        Long courseId = studentCourseRequest.getCourseId();
        if (!inputValidation(studentId, courseId).isEmpty()) {
            responseMessage = inputValidation(studentId, courseId);
        } else if (studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            responseMessage = Utility.message("data_already_exists");
        } else {
            result.setStudent(studentService.getStudentById(studentId));
            result.setCourse(courseService.getCourseById(courseId));
            studentCourseRepository.save(result);
            response = new StudentCourseResponse(result);
            responseMessage = Utility.message("data_added");
        }
        return response;
    }

    // Metode untuk memperbarui informasi mahasiswa memilih matkul melalui repository
    public StudentCourseResponse updateStudentCourse(Long id, StudentCourseRequest studentCourseRequest) {
        StudentCourseResponse response = null;
        Long studentId = studentCourseRequest.getStudentId();
        Long courseId = studentCourseRequest.getCourseId();
        if (getStudentCourseById(id) == null) {
            responseMessage = Utility.message("student_course_not_found");
        } else if (!inputValidation(studentId, courseId).isEmpty()) {
            responseMessage = inputValidation(studentId, courseId);
        } else if (studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            responseMessage = Utility.message("data_already_exists");
        } else {
            StudentCourse studentCourse = getStudentCourseById(id);
            studentCourse.setStudent(studentService.getStudentById(studentId));
            studentCourse.setCourse(courseService.getCourseById(courseId));
            studentCourseRepository.save(studentCourse);
            response = new StudentCourseResponse(studentCourse);
            responseMessage = Utility.message("data_updated");
        }
        return response;
    }

    // Metode untuk memvalidasi apakah id student dan id course ada dalam data
    private String inputValidation(Long studentId, Long courseId) {
        String result = "";
        if (studentId == null) {
            result = "Sorry, id student is required!";
        } else if (courseId == null) {
            result = "Sorry, id course is required!";
        } else if (studentService.getStudentById(studentId) == null) {
            result = Utility.message("student_not_found");
        } else if (courseService.getCourseById(courseId) == null) {
            result = Utility.message("course_not_found");
        }
        return result;
    }
}
