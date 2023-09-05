package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.Grade;
import com.bootcamp.springbootuniversitywgs.repositories.GradeRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    @Autowired
    private StudentCourseService studentCourseService;

    @Autowired
    private Utility utility;

    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Grade> getAllGrade() {
        if (gradeRepository.findAll().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data grade.";
        } else {
            responseMessage = null;
        }
        return gradeRepository.findAll();
    }

    public Grade getGradeById(Long id) {
        Optional<Grade> optionalGrade = gradeRepository.findById(id);
        if (optionalGrade.isPresent()) {
            responseMessage = null;
            return optionalGrade.get();
        } else {
            responseMessage = "Sorry, id grade is not found.";
            return null;
        }
    }

    public Grade insertGrade(String name, Integer grade, Long studentCourseId) {
        Grade newGrade = null;
        if (inputValidation(name, grade, studentCourseId) != "") {
            responseMessage = inputValidation(name, grade, studentCourseId);
        } else {
            newGrade = new Grade(utility.inputTrim(name), grade, studentCourseService.getStudentCourseById(studentCourseId));
            gradeRepository.save(newGrade);
            responseMessage = "Data successfully added!";
        }
        return newGrade;
    }

    public Grade updateGrade(Long id, String name, Integer grade, Long studentCourseId) {
        Grade updateGrade = null;
        if (getGradeById(id) == null) {
            responseMessage = "Sorry, id grade is not found!";
        } else if (inputValidation(name, grade, studentCourseId) != "") {
            responseMessage = inputValidation(name, grade, studentCourseId);
        } else {
            getGradeById(id).setName(utility.inputTrim(name));
            getGradeById(id).setGrade(grade);
            getGradeById(id).setStudentCourse(studentCourseService.getStudentCourseById(studentCourseId));
            updateGrade = getGradeById(id);
            gradeRepository.save(updateGrade);
            responseMessage = "Data successfully updated!";
        }
        return updateGrade;
    }

    private String inputValidation(String name, Integer grade, Long studentCourseId) {
        String result = "";
        if (utility.inputContainsNumber(utility.inputTrim(name)) == 1) {
            result = "Sorry, assignment name cannot be blank.";
        } else if (utility.inputContainsNumber(utility.inputTrim(name)) == 2) {
            result = "Sorry, assignment name can only filled by letters and numbers";
        } else if (utility.gradeCheck(grade) == 1) {
            result = "Sorry, the grades should be between 0-100";
        } else if (studentCourseService.getStudentCourseById(studentCourseId) == null) {
            result = "Sorry, id student course is not found!";
        } else {
            // do nothing
        }
        return result;
    }
}
