package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import in.armando.server.io.pensumStudent.PensumStudentRequest;
import in.armando.server.io.pensumStudent.PensumStudentResponse;
import in.armando.server.service.PensumStudentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/student-pensum")
@RequiredArgsConstructor
public class PensumStudentController {

    private final PensumStudentService service;

    @PostMapping
    public ResponseEntity<PensumStudentResponse> create(@RequestBody PensumStudentRequest request) {
        PensumStudentResponse response = service.createPensumStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public List<PensumStudentResponse> getAll() {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public PensumStudentResponse getById(@PathVariable Long id) {
        return service.getPensumStudentById(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deletePensumStudent(id);
    }

    @PutMapping("/{id}")
    public PensumStudentResponse update(@PathVariable Long id, @RequestBody PensumStudentRequest request) {
        return service.updatePensumStudent(id, request);
    }
}