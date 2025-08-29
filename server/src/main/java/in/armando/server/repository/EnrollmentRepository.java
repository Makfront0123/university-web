package in.armando.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.EnrollmentEntity;

public interface EnrollmentRepository extends JpaRepository<EnrollmentEntity, Long> {
    List<EnrollmentEntity> findByCourseId(Long courseId);

    List<EnrollmentEntity> findByStudentId(Long studentId);

}
