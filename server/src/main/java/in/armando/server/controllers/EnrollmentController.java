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
import in.armando.server.io.enrollment.EnrollmentResponse;
import in.armando.server.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {

    private final EnrollmentService enrollmentService;

    @PostMapping
    public ResponseEntity<ApiResponse<EnrollmentResponse>> createEnrollment(@RequestBody EnrollmentResponse enrollment) {
        EnrollmentResponse response = enrollmentService.createEnrollment(enrollment);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Inscripción creada exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getEnrollmentById(@PathVariable Long id) {
        EnrollmentResponse response = enrollmentService.getEnrollmentById(id);
        return ResponseEntity.ok(new ApiResponse<>("Inscripción encontrada", response));
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getEnrollmentByCourseId(@PathVariable Long courseId) {
        EnrollmentResponse response = enrollmentService.getEnrollmentByCourseId(courseId);
        return ResponseEntity.ok(new ApiResponse<>("Inscripción por curso encontrada", response));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> getEnrollmentByStudentId(@PathVariable Long studentId) {
        EnrollmentResponse response = enrollmentService.getEnrollmentByStudentId(studentId);
        return ResponseEntity.ok(new ApiResponse<>("Inscripción por estudiante encontrada", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
        return ResponseEntity.ok(new ApiResponse<>("Inscripción eliminada exitosamente", null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<EnrollmentResponse>> updateEnrollment(
            @PathVariable Long id,
            @RequestBody EnrollmentResponse enrollment) {
        EnrollmentResponse response = enrollmentService.updateEnrollment(id, enrollment);
        return ResponseEntity.ok(new ApiResponse<>("Inscripción actualizada exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<EnrollmentResponse>>> getAllEnrollments() {
        List<EnrollmentResponse> response = enrollmentService.getAllEnrollments();
        return ResponseEntity.ok(new ApiResponse<>("Lista de inscripciones", response));
    }
}
