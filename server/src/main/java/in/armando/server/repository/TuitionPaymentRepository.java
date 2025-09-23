package in.armando.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.TuitionPaymentEntity;

public interface TuitionPaymentRepository extends JpaRepository<TuitionPaymentEntity, Long> {
    List<TuitionPaymentEntity> findBySemesterId(Long semesterId);

    List<TuitionPaymentEntity> findByStudentId(Long studentId);

    boolean existsByStudentIdAndSemesterId(Long studentId, Long semesterId);
}
