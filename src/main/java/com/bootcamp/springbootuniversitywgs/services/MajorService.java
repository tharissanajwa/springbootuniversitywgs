package com.bootcamp.springbootuniversitywgs.services;

import com.bootcamp.springbootuniversitywgs.dto.requests.MajorRequest;
import com.bootcamp.springbootuniversitywgs.dto.responses.MajorResponse;
import com.bootcamp.springbootuniversitywgs.models.Major;
import com.bootcamp.springbootuniversitywgs.repositories.MajorRepository;
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
    public Page<MajorResponse> getAllMajor(int page, int limit) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<Major> result = majorRepository.findAllByDeletedAtIsNullOrderByName(pageable);
        List<MajorResponse> responses = new ArrayList<>();
        if (result.isEmpty()) {
            responseMessage = "Data doesn't exists, please insert new data major.";
        } else {
            for (Major major : result.getContent()) {
                MajorResponse majorResponse = new MajorResponse(major);
                responses.add(majorResponse);
            }
            responseMessage = "Data successfully displayed.";
        }
        return new PageImpl<>(responses, PageRequest.of(page, limit), result.getTotalElements());
    }

    // Metode untuk mendapatkan data jurusan berdasarkan id melalui repository
    public Major getMajorById(Long id) {
        Optional<Major> optionalMajor = majorRepository.findByIdAndDeletedAtIsNull(id);
        if (optionalMajor.isPresent()) {
            responseMessage = "Data successfully displayed.";
            return optionalMajor.get();
        }
        responseMessage = "Sorry, id major is not found.";
        return null;
    }

    // Metode untuk mendapatkan data jurusan melalui response berdasarkan id melalui repository
    public MajorResponse getMajorByIdResponse(Long id) {
        MajorResponse response = null;
        Major major = getMajorById(id);
        if (major != null) {
            response = new MajorResponse(major);
        }
        return response;
    }

    // Metode untuk menambahkan jurusan baru ke dalam data melalui repository
    public MajorResponse insertMajor(MajorRequest majorRequest) {
        MajorResponse response = null;
        Major result = new Major();
        String name = utility.inputTrim(majorRequest.getName());
        if (!inputValidation(name).isEmpty()) {
            responseMessage = inputValidation(name);
        } else {
            result.setName(name);
            majorRepository.save(result);
            response = new MajorResponse(result);
            responseMessage = "Data successfully added!";
        }
        return response;
    }

    // Metode untuk memperbarui informasi jurusan melalui repository
    public MajorResponse updateMajor(Long id, MajorRequest majorRequest) {
        MajorResponse response = null;
        String name = utility.inputTrim(majorRequest.getName());
        if (inputValidation(name) != "") {
            responseMessage = inputValidation(name);
        } else if (getMajorById(id) != null) {
            Major major = getMajorById(id);
            major.setName(name);
            majorRepository.save(major);
            response = new MajorResponse(major);
            responseMessage = "Data successfully updated!";
        }
        return response;
    }

    // Metode untuk menghapus data jurusan secara soft delete melalui repository
    public boolean disableMajor(Long id) {
        boolean result = false;
        Major major = getMajorById(id);
        if (major != null) {
            major.setDeletedAt(new Date());
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
        }
        return result;
    }
}
