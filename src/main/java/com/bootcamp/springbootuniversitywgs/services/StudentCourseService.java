package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.StudentCourse;
import com.bootcamp.springbootuniversitywgs.repositories.StudentCourseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    public List<StudentCourse> getAllStudentCourse() {
        if (studentCourseRepository.findAll().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data student course.";
        } else {
            responseMessage = "Data successfully displayed.";
        }
        return studentCourseRepository.findAll();
    }

    // Metode untuk mendapatkan data mahasiswa memilih matkul berdasarkan id melalui repository
    public StudentCourse getStudentCourseById(Long id) {
        Optional<StudentCourse> optionalStudentCourse = studentCourseRepository.findById(id);
        if (optionalStudentCourse.isPresent()) {
            responseMessage = "Data successfully displayed.";
            return optionalStudentCourse.get();
        }
        responseMessage = "Sorry, id student course is not found.";
        return null;
    }

    // Metode untuk mendapatkan data mahasiswa memilih matkul berdasarkan id mahasiswa melalui repository
    public List<StudentCourse> getStudentCourseByStudentId(Long studentId) {
        List<StudentCourse> studentCourses = studentCourseRepository.findByStudentId(studentId);
        if (!studentCourses.isEmpty()) {
            responseMessage = "Data successfully displayed.";
            return studentCourses;
        }
        responseMessage = "Sorry, student not found";
        return null;
    }

    // Metode untuk menambahkan data mahasiswa memilih matkul baru ke database melalui repository
    public StudentCourse insertStudentCourse(Long studentId, Long courseId) {
        StudentCourse newStudentCourse = null;
        if (inputValidation(studentId, courseId) != "") {
            responseMessage = inputValidation(studentId, courseId);
        } else if (studentCourseRepository.findByStudentIdAndCourseId(studentId, courseId).isPresent()) {
            responseMessage = "Data already exists!";
        } else {
            newStudentCourse = new StudentCourse(studentService.getStudentById(studentId), courseService.getCourseById(courseId));
            studentCourseRepository.save(newStudentCourse);
            responseMessage = "Data successfully added!";
        }
        return newStudentCourse;
    }

    // Metode untuk memperbarui informasi mahasiswa memilih matkul melalui repository
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

    // Metode untuk memvalidasi apakah id student dan id course ada dalam data
    private String inputValidation(Long studentId, Long courseId) {
        String result = "";
        if (studentId == null) {
            result = "Sorry, id student is required!";
        } else if (courseId == null) {
            result = "Sorry, id course is required!";
        } else if (studentService.getStudentById(studentId) == null) {
            result = "Sorry, id student is not found!";
        } else if (courseService.getCourseById(courseId) == null) {
            result = "Sorry, id course is not found!";
        } else {
            // do nothing
        }
        return result;
    }
}
