package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {
    // Mengambil daftar semua jurusan yang tidak dihapus
    List<Major> findAllByIsDeletedFalse();
    // Mengambil jurusan berdasarkan ID yang tidak dihapus (jika ada)
    Optional<Major> findByIdAndIsDeletedFalse(Long id);
}






