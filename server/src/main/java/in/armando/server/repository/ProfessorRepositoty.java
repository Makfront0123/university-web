package in.armando.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.ProfessorEntity;
import in.armando.server.entity.UserEntity;

public interface ProfessorRepositoty extends JpaRepository<ProfessorEntity, Long> {
    Optional<ProfessorEntity> findByUser(UserEntity user);
    Optional<ProfessorEntity> findByCode(String code);
}
