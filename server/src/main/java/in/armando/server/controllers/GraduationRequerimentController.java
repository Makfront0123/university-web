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
import in.armando.server.io.graduationRequeriment.GraduationRequerimentRequest;
import in.armando.server.io.graduationRequeriment.GraduationRequerimentResponse;
import in.armando.server.service.GraduationRequerimentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/graduation-requeriment")
@RequiredArgsConstructor
public class GraduationRequerimentController {

    private final GraduationRequerimentService service;

    @PostMapping
    public ResponseEntity<ApiResponse<GraduationRequerimentResponse>> create(
            @RequestBody GraduationRequerimentRequest request) {
        GraduationRequerimentResponse response = service.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Requerimiento de graduación creado exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<GraduationRequerimentResponse>> getById(@PathVariable Long id) {
        GraduationRequerimentResponse response = service.getById(id);
        return ResponseEntity.ok(new ApiResponse<>("Requerimiento de graduación encontrado", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Requerimiento de graduación eliminado", null));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<GraduationRequerimentResponse>>> getAll() {
        List<GraduationRequerimentResponse> response = service.getAll();
        return ResponseEntity.ok(new ApiResponse<>("Lista de requerimientos de graduación", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<GraduationRequerimentResponse>> update(@PathVariable Long id,
            @RequestBody GraduationRequerimentRequest request) {
        GraduationRequerimentResponse response = service.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Requerimiento de graduación actualizado", response));
    }
}
