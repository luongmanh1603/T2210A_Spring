package com.example.demo_spring.Service;

import com.example.demo_spring.Repo.ClassRoomRepository;
import com.example.demo_spring.Repo.StudentRepository;
import com.example.demo_spring.enity.ClassRoom;
import com.example.demo_spring.enity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {
    @Autowired
    private ClassRoomRepository classRoomRepository;

    @Autowired
    private StudentRepository studentRepository;



    public List<ClassRoom> getAllClassRooms() {
        return classRoomRepository.findAll();
    }

    public Optional<ClassRoom> getClassRoomById(Integer id) {
        return classRoomRepository.findById(id);
    }

    public ClassRoom saveClassRoom(ClassRoom classRoom) {
        return classRoomRepository.save(classRoom);
    }

    public void deleteClassRoom(Integer id) {
        classRoomRepository.deleteById(id);
    }
    public ClassRoom getClassRoomByName(String name) {
        return classRoomRepository.findByClass_name(name);
    }
    public ClassRoom getClassRoomWithStudents(Integer classId) {
        Optional<ClassRoom> classRoomOptional = classRoomRepository.findById(classId);
        if (classRoomOptional.isPresent()) {
            ClassRoom classRoom = classRoomOptional.get();
            classRoom.setStudents(classRoomRepository.findStudentsInClass(classId));
            return classRoom;
        }
        return null;
    }

    public boolean hasStudents(Integer id) {
        Optional<ClassRoom> classRoom = getClassRoomById(id);
        if (classRoom.isPresent()) {
            return !classRoom.get().getStudents().isEmpty();
        }
        return false;
    }
}
