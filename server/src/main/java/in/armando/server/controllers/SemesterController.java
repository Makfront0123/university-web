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
import in.armando.server.io.semester.SemesterRequest;
import in.armando.server.io.semester.SemesterResponse;
import in.armando.server.service.SemesterService;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/semesters")
@RequiredArgsConstructor
public class SemesterController {

    private final SemesterService semesterService;

    @PostMapping
    public ResponseEntity<ApiResponse<SemesterResponse>> createSemester(@RequestBody SemesterRequest request) {
        SemesterResponse response = semesterService.createSemester(request);
        return ResponseEntity.ok(new ApiResponse<>("Semestre creado exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<SemesterResponse>> getSemesterById(@PathVariable Long id) {
        SemesterResponse response = semesterService.getSemesterById(id);
        return ResponseEntity.ok(new ApiResponse<>("Semestre obtenido exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<SemesterResponse>>> getAllSemesters() {
        List<SemesterResponse> response = semesterService.getAllSemester();
        return ResponseEntity.ok(new ApiResponse<>("Lista de semestres obtenida", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<SemesterResponse>> updateSemester(@PathVariable Long id,
            @RequestBody SemesterRequest request) {
        SemesterResponse response = semesterService.updateSemester(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Semestre actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<SemesterResponse>> deleteSemester(@PathVariable Long id) {
        SemesterResponse response = semesterService.deleteSemester(id);
        return ResponseEntity.ok(new ApiResponse<>("Semestre eliminado exitosamente", response));
    }

}
