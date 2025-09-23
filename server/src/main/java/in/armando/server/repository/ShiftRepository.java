package in.armando.server.repository;

import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import in.armando.server.entity.ShiftEntity;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {
    Optional<ShiftEntity> findByName(String name);

    @Query("SELECT s FROM ShiftEntity s " +
            "WHERE (s.startTime <= :endTime AND s.endTime >= :startTime)")
    Optional<ShiftEntity> findByDateRange(LocalTime startTime, LocalTime endTime);
}
