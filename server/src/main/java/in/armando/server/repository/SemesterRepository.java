package in.armando.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.SemesterEntity;

public interface SemesterRepository extends JpaRepository<SemesterEntity, Long> {
    Optional<SemesterEntity> findByName(String name);
}
