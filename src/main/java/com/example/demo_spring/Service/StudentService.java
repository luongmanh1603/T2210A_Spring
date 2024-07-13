package com.example.demo_spring.Service;

import com.example.demo_spring.Repo.StudentRepository;
import com.example.demo_spring.dto.StudentDTO;
import com.example.demo_spring.enity.ClassRoom;
import com.example.demo_spring.enity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentService {
    @Autowired
    private StudentRepository studentRepository;
    @Autowired
    private ClassRoomService classRoomService;

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }
    public Optional<Student> getStudentById(Long id) {
        return studentRepository.findById(id);
    }
    public Student saveStudent(Student student) {
        Student savedStudent = studentRepository.save(student);
        ClassRoom classRoom = savedStudent.getClassRoom();
        classRoom.updateNumberMember();
        classRoomService.saveClassRoom(classRoom);
        savedStudent.setClassRoom(classRoom); // Update the student with the updated class room
        return studentRepository.save(savedStudent); // Save the student again with the updated class room
    }
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }
    public StudentDTO convertToDTO(Student student) {
        StudentDTO studentDTO = new StudentDTO();
        studentDTO.setId(student.getId());
        studentDTO.setAge(student.getAge());
        studentDTO.setName(student.getName());
        studentDTO.setEmail(student.getEmail());
studentDTO.setClassName(student.getClassRoom().getClass_name());
studentDTO.setClassId(student.getClassRoom().getId_class());

        return studentDTO;
    }
}
