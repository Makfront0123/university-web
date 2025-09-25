package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import in.armando.server.entity.PensumEntity;
import in.armando.server.entity.PensumSubjectEntity;
import in.armando.server.entity.SubjectEntity;

public interface PensumSubjectRepository extends JpaRepository<PensumSubjectEntity, Long> {
    boolean existsByPensumAndSubject(PensumEntity pensum, SubjectEntity subject);

    @Query("""
            SELECT SUM(s.credits)
            FROM PensumSubjectEntity ps
            JOIN ps.subject s
            WHERE ps.pensum.id = :pensumId
            """)
    Integer sumCreditsByPensumId(@Param("pensumId") Long pensumId);
}
