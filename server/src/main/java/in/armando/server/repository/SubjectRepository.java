package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.armando.server.entity.SubjectEntity;

public interface SubjectRepository extends JpaRepository<SubjectEntity, Long> {
    SubjectEntity findByCode(String code);

    @Query("SELECT SUM(s.credits) FROM SubjectEntity s WHERE s.pensum.id = :pensumId")
    Integer sumCreditsByPensumId(@Param("pensumId") Long pensumId);
}
