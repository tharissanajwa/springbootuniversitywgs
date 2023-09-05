package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.models.Major;
import com.bootcamp.springbootuniversitywgs.repositories.MajorRepository;
import com.bootcamp.springbootuniversitywgs.utilities.Utility;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MajorService {
    @Autowired
    private MajorRepository majorRepository;

    @Autowired
    private Utility utility;

    private String responseMessage; // Pesan status untuk memberi informasi kepada pengguna

    // Metode untuk mendapatkan pesan status
    public String getResponseMessage() {
        return responseMessage;
    }

    public List<Major> getAllMajor() {
        if (majorRepository.findAllByIsDeletedFalse().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data major.";
        } else {
            responseMessage = null;
        }
        return majorRepository.findAllByIsDeletedFalse();
    }

    public Major getMajorById(Long id) {
        Optional<Major> optionalMajor = majorRepository.findByIdAndIsDeletedFalse(id);
        if (optionalMajor.isPresent()) {
            responseMessage = null;
            return optionalMajor.get();
        } else {
            responseMessage = "Sorry, id major is not found.";
            return null;
        }
    }

    public Major insertMajor(String name) {
        Major newMajor = null;
        if (inputValidation(name) != "") {
            responseMessage = inputValidation(name);
        } else {
            newMajor = new Major(utility.inputTrim(name));
            majorRepository.save(newMajor);
            responseMessage = "Data successfully added!";
        }
        return newMajor;
    }

    public Major updateMajor(Long id, String name) {
        Major major = null;
        if (inputValidation(name) != "") {
            responseMessage = inputValidation(name);
        } else if (getMajorById(id) != null) {
            getMajorById(id).setName(utility.inputTrim(name));
            major = getMajorById(id);
            majorRepository.save(major);
            responseMessage = "Data successfully updated!";
        }
        return major;
    }

    public boolean disableMajor(Long id) {
        boolean result = false;
        if (getMajorById(id) != null) {
            getMajorById(id).setDeleted(true);
            Major major = getMajorById(id);
            majorRepository.save(major);
            result = true;
            responseMessage = "Data deactivated successfully!";
        }
        return result;
    }

    private String inputValidation(String name) {
        String result = "";
        if (utility.inputCheck(utility.inputTrim(name)) == 1) {
            result = "Sorry, major name cannot be blank.";
        } else if (utility.inputCheck(utility.inputTrim(name)) == 2) {
            result = "Sorry, major name can only filled by letters";
        } else {
            // do nothing
        }
        return result;
    }
}
