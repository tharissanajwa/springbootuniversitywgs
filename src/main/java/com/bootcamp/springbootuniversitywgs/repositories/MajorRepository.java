package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Major;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MajorRepository extends JpaRepository<Major, Long> {
    List<Major> findAllByIsDeletedFalse();
    Optional<Major> findByIdAndIsDeletedFalse(Long id);
}
