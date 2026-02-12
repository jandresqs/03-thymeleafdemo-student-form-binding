package dev.jaqs.thymeleafdemo.controller;

import dev.jaqs.thymeleafdemo.model.Student;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class StudentController {

    @Value( "${countries}")
    private List<String> countries;
    @Value( "${languages}")
    private List<String> languages;
    @Value( "${operatingSystems}")
    private List<String> operatingSystems;

    @GetMapping("/showStudentForm")
    public String showForm(Model model) {

        // create a student object
        Student student = new Student();
        // add student object to the model
        model.addAttribute("student", student);
        // add the list of countries to the model
        model.addAttribute("countries", countries);
        model.addAttribute("languages", languages);
        model.addAttribute("operatingSystems", operatingSystems);

        return "student-form";
    }

    @PostMapping("/processStudentForm")
    public String processStudentForm(@ModelAttribute("student") Student student) {
        return "student-confirmation";
    }

}
