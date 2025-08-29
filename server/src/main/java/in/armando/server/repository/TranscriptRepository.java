package in.armando.server.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.TranscriptEntity;

public interface TranscriptRepository extends JpaRepository<TranscriptEntity, Long> {

    Optional<TranscriptEntity> findByStudent_Id(Long studentId);

    Optional<TranscriptEntity> findBySemester_Id(Long semesterId);

    Optional<TranscriptEntity> findByStudentAndSemester(StudentEntity student, SemesterEntity semester);
}
