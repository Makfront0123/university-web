package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
 
import in.armando.server.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    SubjectEntity findByCode(String code);
}
