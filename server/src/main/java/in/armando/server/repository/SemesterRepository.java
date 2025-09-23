package in.armando.server.repository;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.armando.server.entity.SemesterEntity;

public interface SemesterRepository extends JpaRepository<SemesterEntity, Long> {
    Optional<SemesterEntity> findByName(String name);

    @Query("SELECT s FROM SemesterEntity s " +
            "WHERE (s.startDate <= :endDate AND s.endDate >= :startDate)")
    Optional<SemesterEntity> findByDateRange(LocalDateTime startDate, LocalDateTime endDate);

}
