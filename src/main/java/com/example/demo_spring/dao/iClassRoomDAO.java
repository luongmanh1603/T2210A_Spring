package com.example.demo_spring.dao;

import com.example.demo_spring.enity.ClassRoom;

import java.util.List;

public interface iClassRoomDAO {
    void  saveClassRoom(ClassRoom classRoom);

    ClassRoom getClassRoomById(Long id);
    List<ClassRoom> getAllClassRoom();
}
