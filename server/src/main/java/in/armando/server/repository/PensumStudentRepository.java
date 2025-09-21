package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.PensumEntity;
import in.armando.server.entity.PensumStudentEntity;
import in.armando.server.entity.StudentEntity;

public interface PensumStudentRepository extends JpaRepository<PensumStudentEntity, Long> {
    boolean existsByStudentAndPensum(StudentEntity student, PensumEntity pensum);
}
