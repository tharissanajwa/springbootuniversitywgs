package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.StudentCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentCourseRepository extends JpaRepository<StudentCourse, Long> {
    // Validasi apakah mahasiswa sudah memilih matkul tersebut
    Optional<StudentCourse> findByStudentIdAndCourseId(Long studentId, Long courseId);
    // Mengambil data mahasiswa memilih matkul berdasarkan id mahasiswa
    List<StudentCourse> findByStudentId(Long studentId);
}
