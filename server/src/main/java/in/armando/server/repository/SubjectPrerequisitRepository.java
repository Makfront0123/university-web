package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.SubjectEntity;
import in.armando.server.entity.SubjectPrerequisisitEntity;

public interface SubjectPrerequisitRepository extends JpaRepository<SubjectPrerequisisitEntity, Long> {
    boolean existsBySubjectAndRequiredSubject(SubjectEntity subject, SubjectEntity requiredSubject);
}
