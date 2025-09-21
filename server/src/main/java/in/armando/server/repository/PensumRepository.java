package in.armando.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.armando.server.entity.PensumEntity;

@Repository
public interface PensumRepository extends JpaRepository<PensumEntity, Long> {
    Optional<PensumEntity> findByName(String name);

    boolean existsByName(String name);

    boolean existsByDescription(String description);
}
