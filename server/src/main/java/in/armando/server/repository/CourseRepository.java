package in.armando.server.repository;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import in.armando.server.entity.CourseEntity;
import in.armando.server.entity.ProfessorEntity;
import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.ShiftEntity;

@Repository
public interface CourseRepository extends JpaRepository<CourseEntity, Long> {
    Optional<CourseEntity> findByClassRoom(String classRoom);

    Optional<CourseEntity> findBySubjectId(Long id);

    Optional<CourseEntity> findBySemesterId(Long id);

    Optional<CourseEntity> findByShiftId(Long id);

    Optional<CourseEntity> findByProfessorId(Long id);

    boolean existsByProfessorAndShiftAndSemester(
            ProfessorEntity professor,
            ShiftEntity shift,
            SemesterEntity semester);

    boolean existsByProfessorAndShiftAndSemesterAndIdNot(
            ProfessorEntity professor,
            ShiftEntity shift,
            SemesterEntity semester,
            Long id);

    boolean existsByClassRoomAndShiftAndSemester(
            String classRoom,
            ShiftEntity shift,
            SemesterEntity semester);

    boolean existsByClassRoomAndShiftAndSemesterAndIdNot(
            String classRoom,
            ShiftEntity shift,
            SemesterEntity semester,
            Long id);
}
