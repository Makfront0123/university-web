package in.armando.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.EnrollmentEntity;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    Optional<EnrollmentEntity> findByCourseId(Long courseId);

    Optional<EnrollmentEntity> findByStudentId(Long studentId);

}
