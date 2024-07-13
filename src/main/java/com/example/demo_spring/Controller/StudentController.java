package com.example.demo_spring.Controller;

import com.example.demo_spring.Service.ClassRoomService;
import com.example.demo_spring.enity.ClassRoom;
import org.springframework.ui.Model;
import com.example.demo_spring.Service.StudentService;

import com.example.demo_spring.dto.StudentDTO;
import com.example.demo_spring.enity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import org.springframework.web.multipart.MultipartFile;

//@RestController
//@RequestMapping("/api/students")
//public class StudentController {
//    @Autowired
//    private StudentService studentService;
//
//    @GetMapping
//    public List<Student> getAllStudents() {
//        return studentService.getAllStudents();
//    }
//    @GetMapping("/{id}")
//    public ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) {
//        Optional<Student> student = studentService.getStudentById(id);
//        if (student.isPresent()) {
//            StudentDTO studentDTO = studentService.convertToDTO(student.get());
//            return ResponseEntity.ok(studentDTO);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    @PostMapping
//    public Student createStudent(@RequestBody Student student) {
//        return studentService.saveStudent(student);
//    }
//    @PutMapping("/{id}")
//    public ResponseEntity<Student> updateStudent(@PathVariable Long id, @RequestBody Student studentDetails) {
//        Optional<Student> student = studentService.getStudentById(id);
//        if (student.isPresent()) {
//            Student updatedStudent = student.get();
//            updatedStudent.setName(studentDetails.getName());
//            updatedStudent.setAge(studentDetails.getAge());
//            // Update other fields as needed
//            studentService.saveStudent(updatedStudent);
//            return ResponseEntity.ok(updatedStudent);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
//        if (studentService.getStudentById(id).isPresent()) {
//            studentService.deleteStudent(id);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//}
@Controller
@RequestMapping("/students")
public class StudentController {
    private  StudentService studentService;
    private  ClassRoomService classRoomService;


    @Autowired
    public StudentController(StudentService studentService, ClassRoomService classRoomService) {
        this.studentService = studentService;
        this.classRoomService = classRoomService;
    }

    @GetMapping("")
    public String getAllStudents(Model model) {
        List<Student> students = studentService.getAllStudents();
        model.addAttribute("students", students);
        return "students/index";
    }
    @GetMapping("/create")
    public String showStudentForm(Model model) {
        model.addAttribute("student", new Student());
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        model.addAttribute("classRooms", classRooms);
        return "students/add_student";
    }
    @PostMapping("/create")
    public String createStudent(@ModelAttribute Student student) {
        Optional<ClassRoom> classRoomOptional = classRoomService.getClassRoomById(student.getClassRoom().getId_class());
        if (classRoomOptional.isPresent()) {
            studentService.saveStudent(student);
            return "redirect:/students";
        } else {
            // Handle the case where the ClassRoom does not exist
            // This could be showing an error message to the user
            return "redirect:/students/create?error";
        }
    }
    @GetMapping("/edit/{id}")
    public String editStudent(@PathVariable Long id, Model model) {
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        model.addAttribute("classRooms", classRooms);
        Optional<Student> studentOptional = studentService.getStudentById(id);
        if (studentOptional.isPresent()) {
            model.addAttribute("student", studentOptional.get());
            return "students/edit_student";
        } else {
            return "redirect:/students";
        }
    }
    @PostMapping("/edit/{id}")
    public String updateStudent(@PathVariable Long id, @ModelAttribute Student student) {
        Optional<Student> studentOptional = studentService.getStudentById(id);
        if (studentOptional.isPresent()) {
            Student updatedStudent = studentOptional.get();
            updatedStudent.setName(student.getName());
            updatedStudent.setAge(student.getAge());

            // Get the ClassRoom from the ClassRoomService
            Optional<ClassRoom> classRoomOptional = classRoomService.getClassRoomById(student.getClassRoom().getId_class());
            if (classRoomOptional.isPresent()) {
                // Set the ClassRoom for the Student
                updatedStudent.setClassRoom(classRoomOptional.get());
            } else {
                // Handle the case where the ClassRoom does not exist
                // This could be showing an error message to the user
                return "redirect:/students/edit/" + id + "?error";
            }

            studentService.saveStudent(updatedStudent);
            return "redirect:/students";
        } else {
            return "redirect:/students";
        }
    }
    @GetMapping("/delete/{id}")
    public String deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return "redirect:/students";
    }

}