//package com.example.demo_spring.Controller;
//
//import com.example.demo_spring.Service.ClassRoomService;
//import com.example.demo_spring.enity.ClassRoom;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@RequestMapping("/api/classrooms")
//public class ClassRoomController {
//    @Autowired
//    private ClassRoomService classRoomService;
//
//    @GetMapping
//    public List<ClassRoom> getAllClassRooms() {
//        return classRoomService.getAllClassRooms();
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ClassRoom> getClassRoomById(@PathVariable Integer id) {
//        ClassRoom classRoom = classRoomService.getClassRoomWithStudentCount(id);
//        if (classRoom != null) {
//            return ResponseEntity.ok(classRoom);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public ClassRoom createClassRoom(@RequestBody ClassRoom classRoom) {
//        return classRoomService.saveClassRoom(classRoom);
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ClassRoom> updateClassRoom(@PathVariable Integer id, @RequestBody ClassRoom classRoomDetails) {
//        Optional<ClassRoom> classRoom = classRoomService.getClassRoomById(id);
//        if (classRoom.isPresent()) {
//            ClassRoom updatedClassRoom = classRoom.get();
//            updatedClassRoom.setClass_name(classRoomDetails.getClass_name());
//            updatedClassRoom.setNumber_member(classRoomDetails.getNumber_member());
//            classRoomService.saveClassRoom(updatedClassRoom);
//            return ResponseEntity.ok(updatedClassRoom);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteClassRoom(@PathVariable Integer id) {
//        if (classRoomService.getClassRoomById(id).isPresent()) {
//            classRoomService.deleteClassRoom(id);
//            return ResponseEntity.ok().build();
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//}
//

//MVC
package com.example.demo_spring.Controller;

import com.example.demo_spring.Service.ClassRoomService;
import com.example.demo_spring.enity.ClassRoom;
import com.example.demo_spring.enity.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/classrooms")
public class ClassRoomController {
    private ClassRoomService classRoomService;

    @Autowired
    public ClassRoomController(ClassRoomService classRoomService) {
        this.classRoomService = classRoomService;
    }

    @GetMapping("")
    public String getClassRoomList(Model model) {
        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
        for (ClassRoom classRoom : classRooms) {
            ClassRoom updatedClassRoom = classRoomService.getClassRoomWithStudentCount(classRoom.getId_class());
            if (updatedClassRoom != null) {
                classRoom.setNumber_member(updatedClassRoom.getNumber_member());
            }
        }
        model.addAttribute("classRooms", classRooms);
        return "classroom/index";
    }
    @GetMapping("detail/{id}")
    public  String getClassRoomById(@PathVariable Integer id, Model model) {
        ClassRoom classRoom = classRoomService.getClassRoomWithStudentCount(id);
        if (classRoom != null) {
            List<Student> students = classRoomService.getStudentsByClassRoomId(id);
            model.addAttribute("classRoom", classRoom);
            model.addAttribute("students", students);
            return "classroom/detail_classroom";
        } else {
            return "redirect:/classrooms";
        }
    }


    @GetMapping("/create")
    public String showAddClassRoomForm(Model model) {
        model.addAttribute("classRoom", new ClassRoom());
        return "classroom/add_classroom";
    }
    @PostMapping("/create")
    public String createClassRoom(@ModelAttribute ClassRoom classRoom) {
        classRoomService.saveClassRoom(classRoom);
        return "redirect:/classrooms";
    }
    @GetMapping("/edit/{id}")
    public String editClassRoom(@PathVariable Integer id, Model model) {
        Optional<ClassRoom> classRoomOptional = classRoomService.getClassRoomById(id);
        if (classRoomOptional.isPresent()) {
            model.addAttribute("classRoom", classRoomOptional.get());
            return "classroom/edit_class";
        } else {
            return "redirect:/classrooms";
        }
    }
    @PostMapping("/edit/{id}")
    public String updateClassRoom(@PathVariable Integer id, @ModelAttribute ClassRoom classRoom) {
        Optional<ClassRoom> classRoomOptional = classRoomService.getClassRoomById(id);
        if (classRoomOptional.isPresent()) {
            ClassRoom updatedClassRoom = classRoomOptional.get();
            updatedClassRoom.setClass_name(classRoom.getClass_name());

            classRoomService.saveClassRoom(updatedClassRoom);
        }
        return "redirect:/classrooms";
    }
    @GetMapping("/delete/{id}")
    public String deleteClassRoom(@PathVariable Integer id, RedirectAttributes redirectAttributes) {
        List<Student> students = classRoomService.getStudentsByClassRoomId(id);
        if (students.isEmpty()) {
            classRoomService.deleteClassRoom(id);
            return "redirect:/classrooms";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete class because it has students.");
            return "redirect:/classrooms";
        }
    }


}
