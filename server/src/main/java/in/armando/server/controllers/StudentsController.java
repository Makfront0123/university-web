package in.armando.server.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.students.StudentsRequest;
import in.armando.server.io.students.StudentsResponse;
import in.armando.server.service.StudentsService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/students")
@RequiredArgsConstructor
public class StudentsController {
    private final StudentsService studentService;

    @GetMapping
    public List<StudentsResponse> getAllStudents() {
        return studentService.getAllStudents();
    }

    @GetMapping("/{id}")
    public StudentsResponse getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @GetMapping("/code/{code}")
    public StudentsResponse getStudentByCode(@PathVariable String code) {
        return studentService.getStudentByCode(code);
    }

    @PostMapping
    public StudentsResponse createStudent(@RequestBody StudentsRequest request) {
        return studentService.createStudent(request);
    }

    @PutMapping("/{id}")
    public StudentsResponse updateStudent(@PathVariable Long id, @RequestBody StudentsRequest request) {
        return studentService.updateStudent(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

  
}
