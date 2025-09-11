package in.armando.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.TuitionPaymentEntity;
import in.armando.server.io.tuitionPayment.TuitionPaymentRequest;
import in.armando.server.io.tuitionPayment.TuitionPaymentResponse;
import in.armando.server.repository.TuitionPaymentRepository;
import in.armando.server.service.TuitionPaymentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TuitionPaymentServiceImpl implements TuitionPaymentService {

    private final TuitionPaymentRepository tuitionPaymentRepository;

    @Override
    public TuitionPaymentResponse createPayment(TuitionPaymentRequest payment) {
        TuitionPaymentEntity entity = TuitionPaymentEntity.builder()
                .student(StudentEntity.builder().id(payment.getStudentId()).build())
                .semester(SemesterEntity.builder().id(payment.getSemesterId()).build())
                .tuition(payment.getTuition())
                .status(payment.getStatus())
                .paymentDate(LocalDateTime.now())
                .build();

        TuitionPaymentEntity saved = tuitionPaymentRepository.save(entity);

        return mapToResponse(saved);
    }

    @Override
    public TuitionPaymentResponse updatePayment(Long id, TuitionPaymentRequest request) {
        TuitionPaymentEntity entity = tuitionPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found with id " + id));

        if (request.getStudentId() != null) {
            entity.setStudent(StudentEntity.builder().id(request.getStudentId()).build());
        }
        if (request.getSemesterId() != null) {
            entity.setSemester(SemesterEntity.builder().id(request.getSemesterId()).build());
        }
        if (request.getTuition() != null) {
            entity.setTuition(request.getTuition());
        }
        if (request.getStatus() != null) {
            entity.setStatus(request.getStatus());
        }

        tuitionPaymentRepository.save(entity);
        return mapToResponse(entity);
    }

    @Override
    public void deletePayment(Long id) {
        tuitionPaymentRepository.deleteById(id);
    }

    @Override
    public TuitionPaymentResponse getPaymentById(Long id) {
        TuitionPaymentEntity entity = tuitionPaymentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Payment not found"));
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
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentsByStudent'");
    }

    @Override
    public List<TuitionPaymentResponse> getPaymentsBySemester(Long semesterId) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getPaymentsBySemester'");
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
