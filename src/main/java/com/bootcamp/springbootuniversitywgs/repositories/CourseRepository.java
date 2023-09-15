package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CourseRepository extends JpaRepository<Course, Long> {
    // Mengambil daftar semua matkul yang tidak dihapus
    Page<Course> findAllByDeletedAtIsNullOrderByName(Pageable pageable);
    // Mengambil matkul berdasarkan ID yang tidak dihapus (jika ada)
    Optional<Course> findByIdAndDeletedAtIsNull(Long id);
}
