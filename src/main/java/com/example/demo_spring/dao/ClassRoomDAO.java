package com.example.demo_spring.dao;

import com.example.demo_spring.enity.ClassRoom;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class ClassRoomDAO implements iClassRoomDAO{
    private EntityManager entityManager;
@Autowired
    public ClassRoomDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }


    @Override
    @Transactional
    public void saveClassRoom(ClassRoom classRoom) {

        this.entityManager.persist(classRoom);
    }
    @Override
    public ClassRoom getClassRoomById(Long id){
        return this.entityManager.find(ClassRoom.class,id);
    }

    @Override
    public List<ClassRoom> getAllClassRoom() {
        return this.entityManager.createQuery(" FROM ClassRoom ",ClassRoom.class).getResultList();
    }
}
