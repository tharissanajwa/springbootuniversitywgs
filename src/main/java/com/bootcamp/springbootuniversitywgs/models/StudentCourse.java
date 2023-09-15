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
public class StudentCourse extends BaseModel {
    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @JsonIgnore
    @OneToMany(mappedBy = "studentCourse")
    private List<Grade> grades;

    public StudentCourse() {
        // Constructor default
    }

    // Metode getter setter untuk field-field yg dibutuhkan
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
