package com.example.demo_spring.Repo;

import com.example.demo_spring.enity.ClassRoom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ClassRoomRepository  extends JpaRepository<ClassRoom,Integer> {
    @Query("SELECT COUNT(s) FROM Student s WHERE s.classRoom.id_class = :classId")
    Long countStudentsInClass(@Param("classId") Integer classId);

}
