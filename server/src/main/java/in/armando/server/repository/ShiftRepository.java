package in.armando.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.ShiftEntity;

public interface ShiftRepository extends JpaRepository<ShiftEntity, Long> {
    Optional<ShiftEntity> findByName(String name);
}
