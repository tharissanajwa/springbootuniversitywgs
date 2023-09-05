package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Course;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Mengambil daftar semua matkul yang tidak dihapus
    List<Course> findAllByIsDeletedFalse();
    // Mengambil matkul berdasarkan ID yang tidak dihapus (jika ada)
    Optional<Course> findByIdAndIsDeletedFalse(Long id);
}
