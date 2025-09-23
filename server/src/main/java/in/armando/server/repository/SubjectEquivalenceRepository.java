package in.armando.server.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.SubjectEntity;
import in.armando.server.entity.SubjectEquivalenceEntity;

public interface SubjectEquivalenceRepository extends JpaRepository<SubjectEquivalenceEntity, Long> {
    List<SubjectEquivalenceEntity> findBySubjectId(Long subjectId);

    boolean existsBySubjectAndEquivalentSubject(SubjectEntity subject, SubjectEntity equivalentSubject);
}
