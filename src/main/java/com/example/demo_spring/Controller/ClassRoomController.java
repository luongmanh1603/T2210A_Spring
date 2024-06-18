package com.example.demo_spring.Controller;

import com.example.demo_spring.Service.ClassRoomService;
import com.example.demo_spring.enity.ClassRoom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/classrooms")
public class ClassRoomController {
    @Autowired
    private ClassRoomService classRoomService;

    @GetMapping
    public List<ClassRoom> getAllClassRooms() {
        return classRoomService.getAllClassRooms();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClassRoom> getClassRoomById(@PathVariable Integer id) {
        ClassRoom classRoom = classRoomService.getClassRoomWithStudentCount(id);
        if (classRoom != null) {
            return ResponseEntity.ok(classRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ClassRoom createClassRoom(@RequestBody ClassRoom classRoom) {
        return classRoomService.saveClassRoom(classRoom);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClassRoom> updateClassRoom(@PathVariable Integer id, @RequestBody ClassRoom classRoomDetails) {
        Optional<ClassRoom> classRoom = classRoomService.getClassRoomById(id);
        if (classRoom.isPresent()) {
            ClassRoom updatedClassRoom = classRoom.get();
            updatedClassRoom.setClass_name(classRoomDetails.getClass_name());
            updatedClassRoom.setNumber_member(classRoomDetails.getNumber_member());
            classRoomService.saveClassRoom(updatedClassRoom);
            return ResponseEntity.ok(updatedClassRoom);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteClassRoom(@PathVariable Integer id) {
        if (classRoomService.getClassRoomById(id).isPresent()) {
            classRoomService.deleteClassRoom(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}

