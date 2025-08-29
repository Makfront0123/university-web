package in.armando.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByClassRoom(String classRoom);

    Optional<CourseEntity> findBySubjectId(Long id);

    Optional<CourseEntity> findBySemesterId(Long id);

    Optional<CourseEntity> findByShiftId(Long id);

    Optional<CourseEntity> findByProfessorId(Long id);
}
