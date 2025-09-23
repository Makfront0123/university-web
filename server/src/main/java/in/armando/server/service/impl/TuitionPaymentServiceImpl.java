package in.armando.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.TuitionPaymentEntity;
import in.armando.server.io.tuitionPayment.TuitionPaymentRequest;
import in.armando.server.io.tuitionPayment.TuitionPaymentResponse;
import in.armando.server.repository.SemesterRepository;
import in.armando.server.repository.StudentRepository;
import in.armando.server.repository.TuitionPaymentRepository;
import in.armando.server.service.TuitionPaymentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TuitionPaymentServiceImpl implements TuitionPaymentService {

    private final TuitionPaymentRepository tuitionPaymentRepository;
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;

    @Override
    public TuitionPaymentResponse createPayment(TuitionPaymentRequest payment) {
        if (payment.getTuition() == null || payment.getTuition() <= 0) {
            throw new IllegalArgumentException("Tuition amount must be greater than 0");
        }
        if (payment.getStatus() == null || 
           !(payment.getStatus().equals("PENDING") || payment.getStatus().equals("PAID") || payment.getStatus().equals("CANCELED"))) {
            throw new IllegalArgumentException("Invalid payment status");
        }

        StudentEntity student = studentRepository.findById(payment.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + payment.getStudentId()));

        SemesterEntity semester = semesterRepository.findById(payment.getSemesterId())
                .orElseThrow(() -> new IllegalArgumentException("Semester not found with id " + payment.getSemesterId()));

        boolean exists = tuitionPaymentRepository.existsByStudentIdAndSemesterId(student.getId(), semester.getId());
        if (exists) {
            throw new IllegalStateException("Payment already exists for this student and semester");
        }

        TuitionPaymentEntity entity = TuitionPaymentEntity.builder()
                .student(student)
                .semester(semester)
                .tuition(payment.getTuition())
                .status(payment.getStatus())
                .paymentDate(LocalDateTime.now())
                .build();

        return mapToResponse(tuitionPaymentRepository.save(entity));
    }

    @Override
    public TuitionPaymentResponse updatePayment(Long id, TuitionPaymentRequest request) {
        TuitionPaymentEntity entity = tuitionPaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with id " + id));

        if (request.getStudentId() != null) {
            StudentEntity student = studentRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + request.getStudentId()));
            entity.setStudent(student);
        }
        if (request.getSemesterId() != null) {
            SemesterEntity semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() -> new IllegalArgumentException("Semester not found with id " + request.getSemesterId()));
            entity.setSemester(semester);
        }
        if (request.getTuition() != null) {
            if (request.getTuition() <= 0) {
                throw new IllegalArgumentException("Tuition amount must be greater than 0");
            }
            entity.setTuition(request.getTuition());
        }
        if (request.getStatus() != null) {
            if (!(request.getStatus().equals("PENDING") || request.getStatus().equals("PAID") || request.getStatus().equals("CANCELED"))) {
                throw new IllegalArgumentException("Invalid payment status");
            }
            entity.setStatus(request.getStatus());
        }

        return mapToResponse(tuitionPaymentRepository.save(entity));
    }

    @Override
    public void deletePayment(Long id) {
        if (!tuitionPaymentRepository.existsById(id)) {
            throw new IllegalArgumentException("Payment not found with id " + id);
        }
        tuitionPaymentRepository.deleteById(id);
    }

    @Override
    public TuitionPaymentResponse getPaymentById(Long id) {
        TuitionPaymentEntity entity = tuitionPaymentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Payment not found with id " + id));
        return mapToResponse(entity);
    }

    @Override
    public List<TuitionPaymentResponse> getAllPayments() {
        return tuitionPaymentRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TuitionPaymentResponse> getPaymentsByStudent(Long studentId) {
        return tuitionPaymentRepository.findByStudentId(studentId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<TuitionPaymentResponse> getPaymentsBySemester(Long semesterId) {
        return tuitionPaymentRepository.findBySemesterId(semesterId)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private TuitionPaymentResponse mapToResponse(TuitionPaymentEntity entity) {
        return TuitionPaymentResponse.builder()
                .id(entity.getId())
                .studentId(entity.getStudent().getId())
                .semesterId(entity.getSemester().getId())
                .tuition(entity.getTuition())
                .status(entity.getStatus())
                .paymentDate(entity.getPaymentDate())
                .build();
    }
}
