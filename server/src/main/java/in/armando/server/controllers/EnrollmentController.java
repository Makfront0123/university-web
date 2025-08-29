package in.armando.server.controllers;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.enrollment.EnrollmentResponse;
import in.armando.server.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/enrollments")
@RequiredArgsConstructor
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    @PostMapping
    public EnrollmentResponse createEnrollment(@RequestBody EnrollmentResponse enrollment) {
        return enrollmentService.createEnrollment(enrollment);
    }

    @GetMapping("/{id}")
    public EnrollmentResponse getEnrollmentById(@PathVariable Long id) {
        return enrollmentService.getEnrollmentById(id);
    }

    @GetMapping("/course/{courseId}")
    public EnrollmentResponse getEnrollmentByCourseId(@PathVariable Long courseId) {
        return enrollmentService.getEnrollmentByCourseId(courseId);
    }

    @GetMapping("/student/{studentId}")
    public EnrollmentResponse getEnrollmentByStudentId(@PathVariable Long studentId) {
        return enrollmentService.getEnrollmentByStudentId(studentId);
    }

    @DeleteMapping("/{id}")
    public void deleteEnrollment(@PathVariable Long id) {
        enrollmentService.deleteEnrollment(id);
    }

    @PutMapping("/{id}")
    public EnrollmentResponse updateEnrollment(@PathVariable Long id, @RequestBody EnrollmentResponse enrollment) {
        return enrollmentService.updateEnrollment(id, enrollment);
    }

    @GetMapping
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentService.getAllEnrollments();
    }
}
