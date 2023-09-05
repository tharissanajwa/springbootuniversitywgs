package com.bootcamp.springbootuniversitywgs.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.List;

// Ini adalah kelas StudentCourse yang merepresentasikan data mahasiswa memilih matkul ke database
@Entity
@Table(name = "student_courses")
public class StudentCourse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // Id student course dijadikan auto increment

    // Id mahasiswa sebagai relasi dengan model mahasiswa, dan memiliki hubungan banyak ke satu
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    // Id matkul sebagai relasi dengan model matkul, dan memiliki hubungan banyak ke satu
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonIgnore
    // Hubungan satu-ke-banyak dengan kelas Grade, dan di-mapped oleh properti 'studentCourse' dalam Grade
    @OneToMany(mappedBy = "studentCourse")
    private List<Grade> grades;

    public StudentCourse() {
        // Constructor default
    }

    // Konstruktor untuk membuat objek mahasiswa
    public StudentCourse(Student student, Course course) {
        this.student = student;
        this.course = course;
    }

    // Metode getter setter untuk field-field yg dibutuhkan
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Student getStudent() {
        return student;
    }

    public void setStudent(Student student) {
        this.student = student;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
