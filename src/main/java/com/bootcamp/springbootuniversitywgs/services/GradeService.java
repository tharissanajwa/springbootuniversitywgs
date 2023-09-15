package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.dto.requests.GradeRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.GradeResponse;
import com.bootcamp.springbootuniversitywgs.models.Grade;
import com.bootcamp.springbootuniversitywgs.repositories.GradeRepository;
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

// Kelas ini bertanggung jawab untuk mengelola data nilai
@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentCourseService studentCourseService;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar nilai melalui repository
    public Page<GradeResponse> getAllGrade(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Grade> result = gradeRepository.findAll(pageable);
        List<GradeResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = Utility.message("data_doesnt_exists");
        } else {
            for (Grade grade : result) {
                GradeResponse gradeResponse = new GradeResponse(grade);
                responses.add(gradeResponse);
            }
            responseMessage = Utility.message("data_displayed");
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data nilai berdasarkan id melalui repository
    public Grade getGradeById(Long id) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        if (optionalGrade.isPresent()) {
            responseMessage = Utility.message("data_displayed");
            return optionalGrade.get();
        }
        responseMessage = Utility.message("grade_not_found");
        return null;
    }

    // Metode untuk mendapatkan data nilai melalui response berdasarkan id melalui repository
    public GradeResponse getGradeByIdResponse(Long id) {
        GradeResponse response = null;
        Grade grade = getGradeById(id);
        if (grade != null) {
            response = new GradeResponse(grade);
        }
        return response;
    }

    // Metode untuk mendapatkan data nilai berdasarkan id student course melalui repository
    public Page<GradeResponse> getGradeByStudentCourseId(int page, int limit, Long studentCourseId) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Grade> grades = gradeRepository.findByStudentCourseIdOrderByName(pageable, studentCourseId);
        List<GradeResponse> responses = new ArrayList<>();
        if (!grades.isEmpty()) {
            for (Grade grade : grades.getContent()) {
                GradeResponse gradeResponse = new GradeResponse(grade);
                responses.add(gradeResponse);
            }
            responseMessage = Utility.message("data_displayed");
            return new PageImpl<>(responses, PageRequest.of(page, limit), grades.getTotalElements());
        }
        responseMessage = Utility.message("student_course_not_found");
        return null;
    }

    // Metode untuk menambahkan nilai baru sesuai id student course ke dalam data melalui repository
    public GradeResponse insertGrade(GradeRequest gradeRequest) {
        GradeResponse response = null;
        Grade result = new Grade();
        String name = Utility.inputTrim(gradeRequest.getName());
        Integer grade = gradeRequest.getGrade();
        Long studentCourseId = gradeRequest.getStudentCourseId();
        if (!inputValidation(name, grade, studentCourseId).isEmpty()) {
            responseMessage = inputValidation(name, grade, studentCourseId);
        } else if (gradeRepository.findByNameAndStudentCourseId(name, studentCourseId).isPresent()) {
            responseMessage = Utility.message("data_already_exists");
        } else {
            result.setStudentCourse(studentCourseService.getStudentCourseById(studentCourseId));
            result.setName(name);
            result.setGrade(grade);
            gradeRepository.save(result);
            response = new GradeResponse(result);
            responseMessage = Utility.message("data_added");
        }
        return response;
    }

    // Metode untuk memperbarui informasi nilai melalui repository
    public GradeResponse updateGrade(Long id, GradeRequest gradeRequest) {
        GradeResponse response = null;
        String name = Utility.inputTrim(gradeRequest.getName());
        Integer grade = gradeRequest.getGrade();
        Long studentCourseId = gradeRequest.getStudentCourseId();
        if (getGradeById(id) == null) {
            responseMessage = Utility.message("grade_not_found");
        } else if (!inputValidation(name, grade, studentCourseId).isEmpty()) {
            responseMessage = inputValidation(name, grade, studentCourseId);
        } else if (gradeRepository.findByNameAndStudentCourseId(name, studentCourseId).isPresent()) {
            responseMessage = Utility.message("data_already_exists");
        } else {
            Grade result = getGradeById(id);
            result.setName(name);
            result.setStudentCourse(studentCourseService.getStudentCourseById(studentCourseId));
            gradeRepository.save(result);
            response = new GradeResponse(result);
            responseMessage = Utility.message("data_updated");
        }
        return response;
    }

    // Metode untuk memperbarui informasi nilai melalui repository
    public GradeResponse updateGradeValue(Long id, GradeRequest gradeRequest) {
        GradeResponse response = null;
        Integer grade = gradeRequest.getGrade();
        if (getGradeById(id) == null) {
            responseMessage = Utility.message("grade_not_found");
        } else if (Utility.gradeCheck(grade) == 1) {
            responseMessage = Utility.message("validation_grade");
        }  else {
            Grade result = getGradeById(id);
            result.setGrade(grade);
            gradeRepository.save(result);
            response = new GradeResponse(result);
            responseMessage = Utility.message("data_updated");
        }
        return response;
    }

    // Metode untuk memvalidasi inputan pengguna dan id student course apakah ada atau tidak
    private String inputValidation(String name, Integer grade, Long studentCourseId) {
        String result = "";
        if (Utility.inputContainsNumber(Utility.inputTrim(name)) == 1) {
            result = "Sorry, assignment name cannot be blank.";
        } else if (Utility.inputContainsNumber(Utility.inputTrim(name)) == 2) {
            result = "Sorry, assignment name can only filled by letters and numbers";
        } else if (Utility.gradeCheck(grade) == 1) {
            result = Utility.message("validation_grade");
        } else if (studentCourseId == null) {
            result = "Sorry, id student course is required!";
        } else if (studentCourseService.getStudentCourseById(studentCourseId) == null) {
            result = Utility.message("student_course_not_found");
        }
        return result;
    }
}
