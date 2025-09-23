package in.armando.server.service.impl;

import java.time.LocalDate;
import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.CourseEntity;
import in.armando.server.entity.EnrollmentEntity;
import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.io.enrollment.EnrollmentResponse;
import in.armando.server.repository.CourseRepository;
import in.armando.server.repository.EnrollmentRepository;
import in.armando.server.repository.StudentRepository;
import in.armando.server.repository.TuitionPaymentRepository;
import in.armando.server.service.EnrollmentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class EnrollmentServiceImpl implements EnrollmentService {
    private final CourseRepository courseRepository;
    private final StudentRepository studentRepository;
    private final EnrollmentRepository enrollmentRepository;
    private final TuitionPaymentRepository tuitionPaymentRepository;

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
    public EnrollmentResponse deleteEnrollment(Long id) {
        EnrollmentEntity enrollment = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        enrollmentRepository.delete(enrollment);

        EnrollmentResponse response = new EnrollmentResponse();
        response.setMessage("Matrícula eliminada correctamente");
        response.setId(id);
        return response;
    }

    @Override
    public EnrollmentResponse updateEnrollment(Long id, EnrollmentResponse enrollment) {
        EnrollmentEntity enrollmentEntity = enrollmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Enrollment not found"));

        if (enrollment.getGrade() != null) {
            enrollmentEntity.setGrade(enrollment.getGrade());
        }
        if (enrollment.getStatus() != null) {
            enrollmentEntity.setStatus(enrollment.getStatus());
        }

        EnrollmentEntity updated = enrollmentRepository.save(enrollmentEntity);

        EnrollmentResponse response = mapToResponse(updated);
        response.setMessage("Matrícula actualizada correctamente");
        return response;
    }

    @Override
    public List<EnrollmentResponse> getAllEnrollments() {
        return enrollmentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Transactional
    @Override
    public EnrollmentResponse createEnrollment(EnrollmentResponse enrollment) {
        CourseEntity course = courseRepository.findById(enrollment.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        StudentEntity student = studentRepository.findById(enrollment.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        boolean hasPayment = tuitionPaymentRepository.existsByStudentIdAndSemesterId(student.getId(),
                course.getSemester().getId());
        if (!hasPayment) {
            throw new RuntimeException(
                    "El Estudiante no ha pagado la matrícula del " + course.getSemester().getName());
        }

        boolean alreadyEnrolled = enrollmentRepository.findByCourseIdAndStudentId(course.getId(), student.getId())
                .stream()
                .findAny()
                .isPresent();

        if (alreadyEnrolled) {
            throw new RuntimeException("Student already enrolled");
        }

        long enrolledCount = enrollmentRepository.countByCourseId(course.getId());

        if (enrolledCount >= course.getCapacity()) {
            throw new RuntimeException("Course already full");
        }

        SemesterEntity semester = course.getSemester();

        LocalDate today = LocalDate.now();

        if (semester.getStartDate() != null && today.isBefore(semester.getStartDate().toLocalDate())) {
            throw new RuntimeException("La inscripción ya no está disponible. El semestre comienza el "
                    + semester.getStartDate().toLocalDate());
        }

        if (semester.getEndDate() != null && today.isAfter(semester.getEndDate().toLocalDate())) {
            throw new RuntimeException("La inscripción ya no está disponible. El semestre finaliza el "
                    + semester.getEndDate().toLocalDate());
        }

        EnrollmentEntity enrollmentEntity = EnrollmentEntity.builder()
                .course(course)
                .student(student)
                .enrollmentDate(LocalDate.now())
                .grade(enrollment.getGrade())
                .status(enrollment.getStatus())
                .build();

        EnrollmentEntity saved = enrollmentRepository.save(enrollmentEntity);

        EnrollmentResponse response = mapToResponse(saved);
        response.setMessage("Se ha inscrito correctamente");
        return response;
    }

}
