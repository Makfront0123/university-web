package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.subjectPrerequisit.SubjectPrerequisitRequest;
import in.armando.server.io.subjectPrerequisit.SubjectPrerequisitResponse;
import in.armando.server.service.SubjectPrerequisitService;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/subjectPrerequisit")
@RequiredArgsConstructor
public class SubjectPrerequisitController {

    private final SubjectPrerequisitService subjectPrerequisitService;

    @PostMapping
    public ResponseEntity<SubjectPrerequisitResponse> addSubjectPrerequisit(
            @RequestBody SubjectPrerequisitRequest request) {
        return ResponseEntity.ok(subjectPrerequisitService.addSubjectPrerequisit(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SubjectPrerequisitResponse> getSubjectPrerequisit(
            @PathVariable Long id) {
        return ResponseEntity.ok(subjectPrerequisitService.getSubjectPrerequisit(id));
    }

    @GetMapping
    public ResponseEntity<List<SubjectPrerequisitResponse>> getSubjectPrerequisits() {
        return ResponseEntity.ok(subjectPrerequisitService.getSubjectPrerequisits());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<SubjectPrerequisitResponse> deleteSubjectPrerequisit(
            @PathVariable Long id) {
        return ResponseEntity.ok(subjectPrerequisitService.deleteSubjectPrerequisit(id));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<SubjectPrerequisitResponse> updateSubjectPrerequisit(
            @PathVariable Long id,
            @RequestBody SubjectPrerequisitRequest request) {
        // Puedes decidir si en el servicio pasas también el id, depende de tu lógica
        return ResponseEntity.ok(subjectPrerequisitService.updateSubjectPrerequisit(request));
    }
}
