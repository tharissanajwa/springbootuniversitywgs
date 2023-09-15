package com.bootcamp.springbootuniversitywgs.utilities;

import org.springframework.stereotype.Service;

import java.util.ResourceBundle;

// Kelas ini bertanggung jawab untuk mengelola semua validasi dari inputan pengguna
@Service
public class Utility {
    public static String message(String key) {
        return ResourceBundle.getBundle("response-message").getString(key);
    }

    // Metode untuk menghilangkan spasi berlebih
    public static String inputTrim(String input) {
        String inputTrim;
        if (input == null) {
            inputTrim = null;
        } else {
            inputTrim = input.trim().replaceAll("\\s+", " ");
        }
        return inputTrim;
    }

    // Metode untuk memberikan validasi inputan pengguna
    public static int inputCheck(String input) {
        int valid = 0;
        if (input == null || input.isEmpty()) {
            valid = 1; // Jika inputan pengguna berupa null atau kosong, maka akan memberikan nilai 1(yang berarti error)
        } else if (!input.matches("^[a-zA-Z\\s]+$")) {
            valid = 2; // Jika inputan pengguna selain huruf dan spasi, maka akan memberikan nilai 2(yang berarti error)
        }
        return valid;
    }

    // Metode untuk memberikan validasi inputan pengguna
    public static int inputContainsNumber(String input) {
        int valid = 0;
        if (input == null || input.isEmpty()) {
            valid = 1; // Jika inputan pengguna berupa null atau kosong, maka akan memberikan nilai 1(yang berarti error)
        } else if (!input.matches("^[a-zA-Z0-9\\s]+$")) {
            valid = 2; // Jika inputan pengguna selain huruf, angka dan spasi, maka akan memberikan nilai 2(yang berarti error)
        }
        return valid;
    }

    // Metode untuk memberikan validasi inputan nilai quiz/ujian pengguna
    public static int gradeCheck(Integer grade) {
        int valid = 0;
        if (grade != null) {
            if (grade < 0 || grade > 100) {
                valid = 1; // Jika nilai kurang dari 0 atau lebih dari 100, maka akan memberikan nilai 1(yang berarti error)
            }
        }
        return valid;
    }
}

