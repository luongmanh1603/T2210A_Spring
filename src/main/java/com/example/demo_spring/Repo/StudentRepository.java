package com.example.demo_spring.Repo;

import com.example.demo_spring.enity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StudentRepository  extends JpaRepository<Student,Long> {

}
