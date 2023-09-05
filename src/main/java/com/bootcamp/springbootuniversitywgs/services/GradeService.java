package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.repositories.GradeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class GradeService {
    @Autowired
    private GradeRepository gradeRepository;

    private String responseMessage;

    public String getResponseMessage() {
        return responseMessage;
    }

}
