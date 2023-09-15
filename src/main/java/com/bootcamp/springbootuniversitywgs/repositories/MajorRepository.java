package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Major;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {
    // Mengambil daftar semua jurusan yang tidak dihapus
    Page<Major> findAllByDeletedAtIsNullOrderByName(Pageable pageable);
    // Mengambil jurusan berdasarkan ID yang tidak dihapus (jika ada)
    Optional<Major> findByIdAndDeletedAtIsNull(Long id);
}






