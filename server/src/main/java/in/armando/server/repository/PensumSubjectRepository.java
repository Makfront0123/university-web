package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.PensumEntity;
import in.armando.server.entity.PensumSubjectEntity;
import in.armando.server.entity.SubjectEntity;

public interface PensumSubjectRepository extends JpaRepository<PensumSubjectEntity, Long> {
    boolean existsByPensumAndSubject(PensumEntity pensum, SubjectEntity subject);
}
