package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.ApiResponse;
import in.armando.server.io.subject.SubjectRequest;
import in.armando.server.io.subject.SubjectResponse;
import in.armando.server.service.SubjectService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/subjects")
@RequiredArgsConstructor
public class SubjectController {

    private final SubjectService subjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectResponse>> createSubject(@RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.createSubject(request);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura creada exitosamente", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponse>> updateSubject(@PathVariable Long id,
                                                                      @RequestBody SubjectRequest request) {
        SubjectResponse response = subjectService.updateSubject(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura actualizada exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponse>> deleteSubject(@PathVariable Long id) {
        SubjectResponse response = subjectService.deleteSubject(id);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura eliminada exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectResponse>> getSubjectById(@PathVariable Long id) {
        SubjectResponse response = subjectService.getSubjectById(id);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura encontrada", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectResponse>>> getAllSubjects() {
        List<SubjectResponse> response = subjectService.getAllSubjects();
        return ResponseEntity.ok(new ApiResponse<>("Lista de asignaturas", response));
    }
}
