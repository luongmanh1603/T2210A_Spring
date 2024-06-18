package com.example.demo_spring.Service;

import com.example.demo_spring.Repo.ClassRoomRepository;
import com.example.demo_spring.enity.ClassRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClassRoomService {
    @Autowired
    private ClassRoomRepository classRoomRepository;

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
    //lay so luong hoc sinh trong lop
    public ClassRoom getClassRoomWithStudentCount(Integer id) {
        ClassRoom classRoom = classRoomRepository.findById(id).orElse(null);
        if (classRoom != null) {
            Long studentCount = classRoomRepository.countStudentsInClass(id);
            classRoom.setNumber_member(studentCount.intValue());
        }
        return classRoom;
    }
}
