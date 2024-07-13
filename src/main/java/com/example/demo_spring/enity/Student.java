package com.example.demo_spring.enity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

@Entity
@Table(name = "student")
public class Student {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "age")
    private Integer age;

    @Column(name = "name")
    private String name;

    @Column(name = "email")
    private String email;
    @Column(name = "image")
    private String image;

    @ManyToOne
    @JoinColumn(name = "id_class", referencedColumnName = "id")
    @JsonBackReference
    private ClassRoom classRoom;

    public Student() {
    }

    public Student(Long id, Integer age, String name, String email, String image, ClassRoom classRoom) {
        this.id = id;
        this.age = age;
        this.name = name;
        this.email = email;
        this.image = image;
        this.classRoom = classRoom;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public ClassRoom getClassRoom() {
        return classRoom;
    }

    public void setClassRoom(ClassRoom classRoom) {
        this.classRoom = classRoom;
    }


}