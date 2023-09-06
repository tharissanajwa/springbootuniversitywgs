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

    // Metode untuk mendapatkan semua daftar jurusan yang belum terhapus melalui repository
    public List<Major> getAllMajor() {
        if (majorRepository.findAllByIsDeletedFalse().isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data major.";
        } else {
            responseMessage = "Data successfully displayed.";
        }
        return majorRepository.findAllByIsDeletedFalse();
    }

    // Metode untuk mendapatkan data jurusan berdasarkan id melalui repository
    public Major getMajorById(Long id) {
        Optional<Major> optionalMajor = majorRepository.findByIdAndIsDeletedFalse(id);
        if (optionalMajor.isPresent()) {
            responseMessage = "Data successfully displayed.";
            return optionalMajor.get();
        }
        responseMessage = "Sorry, id major is not found.";
        return null;
    }

    // Metode untuk menambahkan jurusan baru ke dalam data melalui repository
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

    // Metode untuk memperbarui informasi jurusan melalui repository
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

    // Metode untuk menghapus data jurusan secara soft delete melalui repository
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

    // Metode untuk memvalidasi inputan pengguna
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
