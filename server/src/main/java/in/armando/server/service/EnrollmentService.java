package in.armando.server.service;

import java.util.List;

import in.armando.server.io.enrollment.EnrollmentResponse;

public interface EnrollmentService {
    EnrollmentResponse createEnrollment(EnrollmentResponse enrollment);

    EnrollmentResponse getEnrollmentById(Long id);

    EnrollmentResponse getEnrollmentByCourseId(Long courseId);

    EnrollmentResponse getEnrollmentByStudentId(Long studentId);

    EnrollmentResponse deleteEnrollment(Long id);

    EnrollmentResponse updateEnrollment(Long id, EnrollmentResponse enrollment);

    List<EnrollmentResponse> getAllEnrollments();
}
