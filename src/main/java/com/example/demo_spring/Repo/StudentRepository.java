package com.example.demo_spring.Repo;

import com.example.demo_spring.enity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface StudentRepository  extends JpaRepository<Student,Long> {

}
