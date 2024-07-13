package com.example.demo_spring.Service;

import com.example.demo_spring.Repo.SubjectRepository;
import com.example.demo_spring.enity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SubjectService {
    @Autowired
    private SubjectRepository subjectRepository;

    public List<Subject> getAllSubjects() {
        return subjectRepository.findAll();
    }
    public Optional<Subject> getSubjectById(String id) {
        return subjectRepository.findById(id);
    }
    public Subject saveSubject(Subject subject) {
        return subjectRepository.save(subject);
    }
    public void deleteSubject(String id) {
        subjectRepository.deleteById(id);
    }

}
