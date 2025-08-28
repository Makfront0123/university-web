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

import in.armando.server.io.professors.ProfessorRequest;
import in.armando.server.io.professors.ProfessorResponse;
import in.armando.server.service.ProfessorService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/teachers")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @GetMapping
    public List<ProfessorResponse> getAllProfessors() {
        return professorService.getAllProfessors();
    }

    @GetMapping("/{id}")
    public ProfessorResponse getProfessorById(@org.springframework.web.bind.annotation.PathVariable Long id) {
        return professorService.getProfessorById(id);
    }

    @GetMapping("/code/{code}")
    public ProfessorResponse getProfessorByCode(@org.springframework.web.bind.annotation.PathVariable String code) {
        return professorService.getProfessorByCode(code);
    }

    @PostMapping
    public ProfessorResponse createProfessor(@RequestBody ProfessorRequest request) {
        return professorService.createProfessor(request);
    }

    @PutMapping("/{id}")
    public ProfessorResponse updateProfessor(@PathVariable Long id,
            @RequestBody ProfessorRequest request) {
        return professorService.updateProfessor(id, request);
    }

    @DeleteMapping("/{id}")
    public void deleteProfessor(@PathVariable Long id) {
        professorService.deleteProfessor(id);
    }
}
