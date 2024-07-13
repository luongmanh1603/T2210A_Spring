//package com.example.demo_spring.Controller;
//
//import com.example.demo_spring.Service.ClassRoomService;
//import com.example.demo_spring.dto.ClassRoomDTO;
//import com.example.demo_spring.enity.ClassRoom;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.*;
//
//@RestController
//@RequestMapping("/api/classrooms")
//@CrossOrigin(origins = "http://localhost:3000")
//public class ClassRoomController {
//    @Autowired
//    private ClassRoomService classRoomService;
//
//    @GetMapping
//    public List<ClassRoomDTO> getAllClassRooms() {
//        List<ClassRoom> classRooms = classRoomService.getAllClassRooms();
//        List<ClassRoomDTO> classRoomDTOS = new ArrayList<>();
//        for (ClassRoom classRoom : classRooms) {
//            ClassRoomDTO classRoomDTO = new ClassRoomDTO();
//            classRoomDTO.setId(classRoom.getId_class());
//            classRoomDTO.setClass_name(classRoom.getClass_name());
//            classRoomDTO.setNumber_member(classRoom.getNumber_member());
//            classRoomDTOS.add(classRoomDTO);
//        }
//        return classRoomDTOS;
//    }
//
//    @GetMapping("/{id}")
//    public ResponseEntity<ClassRoom> getClassRoomById(@PathVariable Integer id) {
//        ClassRoom classRoom = classRoomService.getClassRoomById(id).orElse(null);
//        if (classRoom != null) {
//            return ResponseEntity.ok(classRoom);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @PostMapping
//    public ResponseEntity<?> createClassRoom(@RequestBody ClassRoom classRoom) {
//        // Kiểm tra xem có ClassRoom nào có cùng tên đã tồn tại chưa
//        ClassRoom existingClassRoom = classRoomService.getClassRoomByName(classRoom.getClass_name());
//        if (existingClassRoom != null) {
//            // Nếu có, trả về một lỗi
//            return ResponseEntity.badRequest().body("A ClassRoom with the same name already exists.");
//        } else {
//            ClassRoom savedClassRoom = classRoomService.saveClassRoom(classRoom);
//            Map<String, Object> response = new HashMap<>();
//            response.put("message", "ClassRoom has been created successfully");
//            response.put("classRoom", savedClassRoom);
//            return ResponseEntity.ok(response);
//        }
//    }
//
//    @PutMapping("/{id}")
//    public ResponseEntity<ClassRoom> updateClassRoom(@PathVariable Integer id, @RequestBody ClassRoom classRoomDetails) {
//        Optional<ClassRoom> classRoom = classRoomService.getClassRoomById(id);
//        if (classRoom.isPresent()) {
//            ClassRoom updatedClassRoom = classRoom.get();
//            updatedClassRoom.setClass_name(classRoomDetails.getClass_name());
//
//            classRoomService.saveClassRoom(updatedClassRoom);
//            return ResponseEntity.ok(updatedClassRoom);
//        } else {
//            return ResponseEntity.notFound().build();
//        }
//    }
//
//    @DeleteMapping("/{id}")
//    public ResponseEntity<?> deleteClassRoom(@PathVariable Integer id) {
//        if (!classRoomService.getClassRoomById(id).isPresent()) {
//            return ResponseEntity.notFound().build();
//        }
//
//        // Kiểm tra xem lớp học có học sinh không
//        if (classRoomService.hasStudents(id)) {
//            // Trả về một phản hồi không cho phép xóa vì lớp học vẫn còn học sinh
//            return ResponseEntity.badRequest().body("Cannot delete class room as it still has students.");
//        }
//
//        classRoomService.deleteClassRoom(id);
//        return ResponseEntity.ok().build();
//    }
//}


////MVC
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
            ClassRoom updatedClassRoom = classRoomService.getClassRoomWithStudents(classRoom.getId_class());
            if (updatedClassRoom != null) {
                classRoom.setNumber_member(updatedClassRoom.getNumber_member());
            }
        }
        model.addAttribute("classRooms", classRooms);
        return "classroom/index";
    }
    @GetMapping("detail/{id}")
    public  String getClassRoomById(@PathVariable Integer id, Model model) {
        ClassRoom classRoom = classRoomService.getClassRoomWithStudents(id);
        if (classRoom != null) {
            List<Student> students = classRoom.getStudents();
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
        List<Student> students = classRoomService.getClassRoomWithStudents(id).getStudents();
        if (students.isEmpty()) {
            classRoomService.deleteClassRoom(id);
            return "redirect:/classrooms";
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Cannot delete class because it has students.");
            return "redirect:/classrooms";
        }
    }


}
