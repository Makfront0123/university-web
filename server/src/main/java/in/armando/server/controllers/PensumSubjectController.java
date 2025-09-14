
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
import in.armando.server.io.pensumSubject.PensumSubjectRequest;
import in.armando.server.io.pensumSubject.PensumSubjectResponse;
import in.armando.server.service.PensumSubjectService;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/pensum-subject")
@RequiredArgsConstructor
public class PensumSubjectController {

    private final PensumSubjectService pensumSubjectService;

    @PostMapping
    public ResponseEntity<ApiResponse<PensumSubjectResponse>> create(@RequestBody PensumSubjectRequest request) {
        PensumSubjectResponse response = pensumSubjectService.create(request);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura del pensum creada exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PensumSubjectResponse>>> getAll() {
        List<PensumSubjectResponse> response = pensumSubjectService.getAll();
        return ResponseEntity.ok(new ApiResponse<>("Lista de asignaturas del pensum", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PensumSubjectResponse>> getById(@PathVariable Long id) {
        PensumSubjectResponse response = pensumSubjectService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura del pensum encontrada", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        pensumSubjectService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura del pensum eliminada exitosamente", null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PensumSubjectResponse>> update(@PathVariable Long id,
                                                                     @RequestBody PensumSubjectRequest request) {
        PensumSubjectResponse response = pensumSubjectService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Asignatura del pensum actualizada exitosamente", response));
    }
}
