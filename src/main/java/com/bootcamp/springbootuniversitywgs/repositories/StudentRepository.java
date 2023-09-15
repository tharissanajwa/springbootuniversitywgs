package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    // Mengambil daftar semua mahasiswa yang tidak dihapus
    List<Student> findAllByDeletedAtIsNullOrderByName();
    // Mengambil matkul berdasarkan ID yang tidak dihapus (jika ada)
    Optional<Student> findByIdAndDeletedAtIsNull(Long id);
}
