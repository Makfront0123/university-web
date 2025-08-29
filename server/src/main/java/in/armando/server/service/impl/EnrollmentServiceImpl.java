package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.CourseEntity;
import in.armando.server.entity.EnrollmentEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.io.enrollment.EnrollmentResponse;
import in.armando.server.repository.CourseRepository;
import in.armando.server.repository.EnrollmentRepository;
import in.armando.server.repository.StudentRepository;
import in.armando.server.service.EnrollmentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;

    @Override
    public EnrollmentResponse getEnrollmentById(Long id) {
        EnrollmentEntity enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));
        return mapToResponse(enrollment);
    }

    private EnrollmentResponse mapToResponse(EnrollmentEntity enrollment) {
        return EnrollmentResponse.builder()
                .id(enrollment.getId())
                .courseId(enrollment.getCourse().getId())
                .studentId(enrollment.getStudent().getId())
                .enrollmentDate(enrollment.getEnrollmentDate())
                .grade(enrollment.getGrade())
                .status(enrollment.getStatus())
                .build();
    }

    @Override
    public EnrollmentResponse getEnrollmentByCourseId(Long courseId) {
        EnrollmentEntity enrollment = enrollmentRepository.findByCourseId(courseId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Enrollment not found for course " + courseId));

        return mapToResponse(enrollment);
    }

    @Override
    public EnrollmentResponse getEnrollmentByStudentId(Long studentId) {
        EnrollmentEntity enrollment = enrollmentRepository.findByStudentId(studentId)
                .stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Enrollment not found for student " + studentId));

        return mapToResponse(enrollment);
    }

    @Override
    public void deleteEnrollment(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public EnrollmentResponse updateEnrollment(Long id, EnrollmentResponse enrollment) {
        EnrollmentEntity enrollmentEntity = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollmentEntity.setGrade(enrollment.getGrade());
        enrollmentEntity.setStatus(enrollment.getStatus());

        EnrollmentEntity updated = enrollmentRepository.save(enrollmentEntity);

        return mapToResponse(updated);
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EnrollmentResponse createEnrollment(EnrollmentResponse enrollment) {
        CourseEntity course = courseRepository.findById(enrollment.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        StudentEntity student = studentRepository.findById(enrollment.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .course(course)
                .student(student)
                .enrollmentDate(enrollment.getEnrollmentDate())
                .grade(enrollment.getGrade())
                .status(enrollment.getStatus())
                .build();

        EnrollmentEntity saved = enrollmentRepository.save(enrollmentEntity);

        return mapToResponse(saved);
    }

}
