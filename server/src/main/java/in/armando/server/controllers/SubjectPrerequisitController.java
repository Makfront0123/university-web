package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
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

import in.armando.server.io.ApiResponse;
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
    public ResponseEntity<ApiResponse<SubjectPrerequisitResponse>> addSubjectPrerequisit(
            @RequestBody SubjectPrerequisitRequest request) {
        SubjectPrerequisitResponse response = subjectPrerequisitService.addSubjectPrerequisit(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Prerrequisito agregado exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectPrerequisitResponse>> getSubjectPrerequisit(
            @PathVariable Long id) {
        SubjectPrerequisitResponse response = subjectPrerequisitService.getSubjectPrerequisit(id);
        return ResponseEntity.ok(new ApiResponse<>("Prerrequisito encontrado", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SubjectPrerequisitResponse>>> getSubjectPrerequisits() {
        List<SubjectPrerequisitResponse> response = subjectPrerequisitService.getSubjectPrerequisits();
        return ResponseEntity.ok(new ApiResponse<>("Lista de prerrequisitos", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SubjectPrerequisitResponse>> updateSubjectPrerequisit(
            @PathVariable Long id,
            @RequestBody SubjectPrerequisitRequest request) {
        SubjectPrerequisitResponse response = subjectPrerequisitService.updateSubjectPrerequisit(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Prerrequisito actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteSubjectPrerequisit(@PathVariable Long id) {
        subjectPrerequisitService.deleteSubjectPrerequisit(id);
        return ResponseEntity.ok(new ApiResponse<>("Prerrequisito eliminado exitosamente", null));
    }
}
