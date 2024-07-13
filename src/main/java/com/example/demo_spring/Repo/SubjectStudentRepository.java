package com.example.demo_spring.Repo;

import com.example.demo_spring.enity.SubjectStudent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubjectStudentRepository extends JpaRepository<SubjectStudent, Integer> {
}
