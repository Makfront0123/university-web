package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
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
import in.armando.server.io.subjectEquivalence.SubjectEquivalenceRequest;
import in.armando.server.io.subjectEquivalence.SubjectEquivalenceResponse;
import in.armando.server.service.SubjectEquivalenceService;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/subject-equivalence")
@RequiredArgsConstructor
public class SubjectEquivalenceController {

    private final SubjectEquivalenceService subjectEquivalenceService;

    @PostMapping
    public ResponseEntity<ApiResponse<SubjectEquivalenceResponse>> addSubjectEquivalence(
            @RequestBody SubjectEquivalenceRequest request) {
        SubjectEquivalenceResponse response = subjectEquivalenceService.addSubjectEquivalence(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Equivalencia agregada exitosamente", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectEquivalenceResponse>> updateSubjectEquivalence(
            @PathVariable Long id,
            @RequestBody SubjectEquivalenceRequest request) {
        SubjectEquivalenceResponse response = subjectEquivalenceService.updateSubjectEquivalence(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Equivalencia actualizada exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectEquivalenceResponse>> getSubjectEquivalence(@PathVariable Long id) {
        SubjectEquivalenceResponse response = subjectEquivalenceService.getSubjectEquivalence(id);
        return ResponseEntity.ok(new ApiResponse<>("Equivalencia encontrada", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectEquivalenceResponse>>> getSubjectEquivalences() {
        List<SubjectEquivalenceResponse> response = subjectEquivalenceService.getSubjectEquivalences();
        return ResponseEntity.ok(new ApiResponse<>("Lista de equivalencias", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSubjectEquivalence(@PathVariable Long id) {
        subjectEquivalenceService.deleteSubjectEquivalence(id);
        return ResponseEntity.ok(new ApiResponse<>("Equivalencia eliminada exitosamente", null));
    }
}
