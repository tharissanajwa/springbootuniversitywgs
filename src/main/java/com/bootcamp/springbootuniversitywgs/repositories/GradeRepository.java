package com.bootcamp.springbootuniversitywgs.repositories;

import com.bootcamp.springbootuniversitywgs.models.Grade;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GradeRepository extends JpaRepository<Grade, Long> {
}
