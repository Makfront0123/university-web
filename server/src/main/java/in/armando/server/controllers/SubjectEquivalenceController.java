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
    public ResponseEntity<SubjectEquivalenceResponse> addSubjectEquivalence(
            @RequestBody SubjectEquivalenceRequest request) {
        return ResponseEntity.ok(subjectEquivalenceService.addSubjectEquivalence(request));
    }

    @PatchMapping
    public ResponseEntity<SubjectEquivalenceResponse> updateSubjectEquivalence(
            @RequestBody SubjectEquivalenceRequest request) {
        return ResponseEntity.ok(subjectEquivalenceService.updateSubjectEquivalence(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectEquivalenceResponse> getSubjectEquivalence(@PathVariable Long id) {
        return ResponseEntity.ok(subjectEquivalenceService.getSubjectEquivalence(id));
    }

    @GetMapping
    public List<SubjectEquivalenceResponse> getSubjectEquivalences() {
        return subjectEquivalenceService.getSubjectEquivalences();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectEquivalenceResponse> deleteSubjectEquivalence(@PathVariable Long id) {
        return ResponseEntity.ok(subjectEquivalenceService.deleteSubjectEquivalence(id));
    }
}
