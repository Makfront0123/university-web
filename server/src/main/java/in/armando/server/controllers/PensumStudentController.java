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

import in.armando.server.io.ApiResponse;
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
    public ResponseEntity<ApiResponse<PensumStudentResponse>> create(@RequestBody PensumStudentRequest request) {
        PensumStudentResponse response = service.createPensumStudent(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Registro de estudiante en pensum creado exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PensumStudentResponse>>> getAll() {
        List<PensumStudentResponse> response = service.getAll();
        return ResponseEntity.ok(new ApiResponse<>("Lista de registros de estudiantes en pensum", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PensumStudentResponse>> getById(@PathVariable Long id) {
        PensumStudentResponse response = service.getPensumStudentById(id);
        return ResponseEntity.ok(new ApiResponse<>("Registro de estudiante en pensum encontrado", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> delete(@PathVariable Long id) {
        service.deletePensumStudent(id);
        return ResponseEntity.ok(new ApiResponse<>("Registro de estudiante en pensum eliminado", null));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<PensumStudentResponse>> update(@PathVariable Long id,
            @RequestBody PensumStudentRequest request) {
        PensumStudentResponse response = service.updatePensumStudent(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Registro de estudiante en pensum actualizado", response));
    }
}
