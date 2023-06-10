package casfu.cmpt276.assignment2.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import casfu.cmpt276.assignment2.model.Student;
import casfu.cmpt276.assignment2.model.StudentRepository;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class StudentsController {

    @Autowired
    private StudentRepository studentRepo;

    @GetMapping("/students/view")
    public String getAllStudents(Model model) {
        System.out.println("Getting all students:");
        List<Student> students = studentRepo.findAll();
        model.addAttribute("std", students);
        return "students/showAll";
    }

    @GetMapping("/students/display")
    public String showAllStudents(Model model) {
        System.out.println("Getting all students:");
        List<Student> students = studentRepo.findAll();
        model.addAttribute("std", students);
        return "students/tables";
    }

    @PostMapping("/students/add")
    public String addStudent(@RequestParam Map<String, String> newStudent,
            HttpServletResponse response) {
        System.out.println("ADD student");
        String newName = newStudent.get("name");
        double newWeight = Double.parseDouble(newStudent.get("weight"));
        double newHeight = Double.parseDouble(newStudent.get("height"));
        double newGpa = Double.parseDouble(newStudent.get("gpa"));
        String newMajor = newStudent.get("major");
        studentRepo.save(new Student(newName, newWeight, newHeight, newGpa,
                newMajor));
        response.setStatus(201);
        return "redirect:/students/view";
    }

    @GetMapping("/students/edit/{stid}")
    public String editStudentFunction(@PathVariable int stid, Model model) {
        model.addAttribute("student", studentRepo.findById(stid).get());
        return "students/editStudent";
    }

    @PostMapping("/students/{id}")
    public String updateStudent(@PathVariable int id, @RequestParam Map<String, String> student, Model model) {
        Student theStudent = studentRepo.findById(id).get();
        theStudent.setName(student.get("name"));
        theStudent.setWeight(Double.parseDouble(student.get("weight")));
        theStudent.setHeight(Double.parseDouble(student.get("height")));
        theStudent.setGpa(Double.parseDouble(student.get("gpa")));
        theStudent.setMajor(student.get("major"));

        studentRepo.save(theStudent);
        return "redirect:/students/view";
    }

    @GetMapping("/students/{id}")
    public String deleteStudent(@PathVariable int id) {
        studentRepo.deleteById(id);
        return "redirect:/students/view";
    }

}
