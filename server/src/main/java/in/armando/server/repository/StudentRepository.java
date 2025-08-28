package in.armando.server.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.UserEntity;
@Repository
public interface StudentRepository extends JpaRepository<StudentEntity, Long> {
    Optional<StudentEntity> findByUser(UserEntity user);
    Optional<StudentEntity> findByCode(String code);
}
