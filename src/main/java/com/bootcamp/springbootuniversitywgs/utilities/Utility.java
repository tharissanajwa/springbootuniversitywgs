package com.bootcamp.springbootuniversitywgs.utilities;

import org.springframework.stereotype.Service;

// Kelas ini bertanggung jawab untuk mengelola semua validasi dari inputan pengguna
@Service
public class Utility {

    // Metode untuk menghilangkan spasi berlebih
    public String inputTrim(String input) {
        String inputTrim;
        if (input == null) {
            inputTrim = null;
        } else {
            inputTrim = input.trim().replaceAll("\\s+", " ");
        }
        return inputTrim;
    }

    // Metode untuk memberikan validasi inputan pengguna
    public int inputCheck(String input) {
        int valid = 0;
        if (input == null || input.isEmpty()) {
            valid = 1; // Jika inputan pengguna berupa null atau kosong, maka akan memberikan nilai 1(yang berarti error)
        } else if (!input.matches("^[a-zA-Z\\s]+$")) {
            valid = 2; // Jika inputan pengguna selain huruf dan spasi, maka akan memberikan nilai 2(yang berarti error)
        }
        return valid;
    }

    // Metode untuk memberikan validasi inputan nilai quiz/ujian pengguna
    public int gradeCheck(Integer grade) {
        int valid = 0;
        if (grade != null) {
            if (grade < 0 || grade > 100) {
                valid = 1; // Jika nilai kurang dari 0 atau lebih dari 100, maka akan memberikan nilai 1(yang berarti error)
            }
        }
        return valid;
    }
}

