package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface GradeRepository extends JpaRepository<Grade, Long> {
    // Validasi apakah student course sudah diberi nilai
    Optional<Grade> findByNameAndStudentCourseId(String name, Long studentCourseId);
    // Mengambil data nilai berdasarkan id student course
    List<Grade> findByStudentCourseId(Long studentCourseId);
}
