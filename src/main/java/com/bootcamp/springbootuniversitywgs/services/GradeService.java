package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.dto.requests.GradeRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.GradeResponse;
import com.bootcamp.springbootuniversitywgs.models.Grade;
import com.bootcamp.springbootuniversitywgs.repositories.GradeRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private Utility utility;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    // Metode untuk mendapatkan semua daftar nilai melalui repository
    public List<GradeResponse> getAllGrade() {
        List<Grade> result = gradeRepository.findAll();
        List<GradeResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data grade.";
        } else {
            for (Grade grade : result) {
                GradeResponse gradeResponse = new GradeResponse(grade);
                responses.add(gradeResponse);
            }
            responseMessage = "Data successfully displayed.";
        }
        return responses;
    }

    // Metode untuk mendapatkan data nilai berdasarkan id melalui repository
    public Grade getGradeById(Long id) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        if (optionalGrade.isPresent()) {
            responseMessage = "Data successfully displayed.";
            return optionalGrade.get();
        }
        responseMessage = "Sorry, id grade is not found.";
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
    public List<GradeResponse> getGradeByStudentCourseId(Long studentCourseId) {
        List<Grade> grades = gradeRepository.findByStudentCourseIdOrderByName(studentCourseId);
        List<GradeResponse> responses = new ArrayList<>();
        if (!grades.isEmpty()) {
            for (Grade grade : grades) {
                GradeResponse gradeResponse = new GradeResponse(grade);
                responses.add(gradeResponse);
            }
            responseMessage = "Data successfully displayed.";
            return responses;
        }
        responseMessage = "Sorry, student course not found.";
        return null;
    }

    // Metode untuk menambahkan nilai baru sesuai id student course ke dalam data melalui repository
    public GradeResponse insertGrade(GradeRequest gradeRequest) {
        GradeResponse response = null;
        Grade result = new Grade();
        String name = utility.inputTrim(gradeRequest.getName());
        Integer grade = gradeRequest.getGrade();
        Long studentCourseId = gradeRequest.getStudentCourseId();
        if (!inputValidation(name, grade, studentCourseId).isEmpty()) {
            responseMessage = inputValidation(name, grade, studentCourseId);
        } else if (gradeRepository.findByNameAndStudentCourseId(name, studentCourseId).isPresent()) {
            responseMessage = "Data already exists!";
        } else {
            result.setStudentCourse(studentCourseService.getStudentCourseById(studentCourseId));
            result.setName(name);
            result.setGrade(grade);
            gradeRepository.save(result);
            response = new GradeResponse(result);
            responseMessage = "Data successfully added!";
        }
        return response;
    }

    // Metode untuk memperbarui informasi nilai melalui repository
    public GradeResponse updateGrade(Long id, GradeRequest gradeRequest) {
        GradeResponse response = null;
        String name = utility.inputTrim(gradeRequest.getName());
        Integer grade = gradeRequest.getGrade();
        Long studentCourseId = gradeRequest.getStudentCourseId();
        if (getGradeById(id) == null) {
            responseMessage = "Sorry, id grade is not found!";
        } else if (!inputValidation(name, grade, studentCourseId).isEmpty()) {
            responseMessage = inputValidation(name, grade, studentCourseId);
        } else if (gradeRepository.findByNameAndStudentCourseId(name, studentCourseId).isPresent()) {
            responseMessage = "Data already exists!";
        } else {
            Grade result = getGradeById(id);
            result.setName(name);
            result.setStudentCourse(studentCourseService.getStudentCourseById(studentCourseId));
            gradeRepository.save(result);
            response = new GradeResponse(result);
            responseMessage = "Data successfully updated!";
        }
        return response;
    }

    // Metode untuk memperbarui informasi nilai melalui repository
    public GradeResponse updateGradeValue(Long id, GradeRequest gradeRequest) {
        GradeResponse response = null;
        Integer grade = gradeRequest.getGrade();
        if (getGradeById(id) == null) {
            responseMessage = "Sorry, id grade is not found!";
        } else if (utility.gradeCheck(grade) == 1) {
            responseMessage = "Sorry, the grades should be between 0-100";
        }  else {
            Grade result = getGradeById(id);
            result.setGrade(grade);
            gradeRepository.save(result);
            response = new GradeResponse(result);
            responseMessage = "Data successfully updated!";
        }
        return response;
    }

    // Metode untuk memvalidasi inputan pengguna dan id student course apakah ada atau tidak
    private String inputValidation(String name, Integer grade, Long studentCourseId) {
        String result = "";
        if (utility.inputContainsNumber(utility.inputTrim(name)) == 1) {
            result = "Sorry, assignment name cannot be blank.";
        } else if (utility.inputContainsNumber(utility.inputTrim(name)) == 2) {
            result = "Sorry, assignment name can only filled by letters and numbers";
        } else if (utility.gradeCheck(grade) == 1) {
            result = "Sorry, the grades should be between 0-100";
        } else if (studentCourseId == null) {
            result = "Sorry, id student course is required!";
        } else if (studentCourseService.getStudentCourseById(studentCourseId) == null) {
            result = "Sorry, id student course is not found!";
        }
        return result;
    }
}
