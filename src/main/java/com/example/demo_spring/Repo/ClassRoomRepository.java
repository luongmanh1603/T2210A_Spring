package com.example.demo_spring.Repo;

import com.example.demo_spring.enity.ClassRoom;
import com.example.demo_spring.enity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;
@Repository
public interface ClassRoomRepository  extends JpaRepository<ClassRoom,Integer> {
    @Query("SELECT COUNT(s) FROM Student s WHERE s.classRoom.id_class = :classId")
    Long countStudentsInClass(@Param("classId") Integer classId);
    @Query("SELECT s FROM Student s WHERE s.classRoom.id_class = :classId")
    List<Student> findStudentsInClass(@Param("classId") Integer classId);
    @Query("SELECT c FROM ClassRoom c WHERE c.class_name = :className")
    ClassRoom findByClass_name(@Param("className") String class_name);

}
