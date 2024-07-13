package com.example.demo_spring.Controller;

import com.example.demo_spring.Service.SubjectService;
import com.example.demo_spring.enity.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/subjects")
public class SubjectController {
    @Autowired
     SubjectService subjectService;



    @GetMapping("")
    public String getAllSubjects(Model model) {
        List<Subject> subjects = subjectService.getAllSubjects();
        model.addAttribute("subjects", subjects);

        return "subject/index";
    }
    @GetMapping("/create")
    public String ShowAddSubjectForm(Model model) {
        model.addAttribute("subject", new Subject());
        return "subject/create";
    }
    @PostMapping("/create")
    public String createSubject(Subject subject) {
        subjectService.saveSubject(subject);
        return "redirect:/subjects";
    }
}
