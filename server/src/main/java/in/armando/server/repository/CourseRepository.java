package in.armando.server.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.CourseEntity;

public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    CourseEntity findByClassRoom(String classRoom);

    CourseEntity findBySubjectId(Long subjectId);

    CourseEntity findBySemesterId(Long semesterId);

    CourseEntity findByShiftId(Long shiftId);
}
