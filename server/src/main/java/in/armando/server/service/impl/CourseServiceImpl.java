package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.CourseEntity;
import in.armando.server.entity.ProfessorEntity;
import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.ShiftEntity;
import in.armando.server.entity.SubjectEntity;
import in.armando.server.io.course.CourseRequest;
import in.armando.server.io.course.CourseResponse;
import in.armando.server.io.professors.ProfessorResponse;
import in.armando.server.io.role.RoleResponse;
import in.armando.server.io.semester.SemesterResponse;
import in.armando.server.io.shift.ShiftResponse;
import in.armando.server.io.subject.SubjectResponse;
import in.armando.server.io.user.UserResponse;
import in.armando.server.repository.CourseRepository;
import in.armando.server.repository.ProfessorRepository;
import in.armando.server.repository.SemesterRepository;
import in.armando.server.repository.ShiftRepository;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.CourseService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
        private final CourseRepository courseRepository;
        private final SubjectRepository subjectRepository;
        private final SemesterRepository semesterRepository;
        private final ShiftRepository shiftRepository;
        private final ProfessorRepository professorRepository;
 

        private CourseResponse mapToResponse(CourseEntity course) {
                return CourseResponse.builder()
                                .id(course.getId())
                                .classRoom(course.getClassRoom())
                                .capacity(course.getCapacity())
                                .subject(course.getSubject() != null ? new SubjectResponse(
                                                course.getSubject().getId(),
                                                course.getSubject().getCode(),
                                                course.getSubject().getName(),
                                                course.getSubject().getCredits(),
                                                null) : null)
                                .semester(course.getSemester() != null ? new SemesterResponse(
                                                course.getSemester().getId(),
                                                course.getSemester().getName(),
                                                course.getSemester().getStartDate(),
                                                course.getSemester().getEndDate(),
                                                null) : null)
                                .shift(course.getShift() != null ? new ShiftResponse(
                                                course.getShift().getId(),
                                                course.getShift().getName(),
                                                course.getShift().getStartTime(),
                                                course.getShift().getEndTime(),
                                                course.getShift().getDayOfWeek(),
                                                null) : null)
                                .professor(course.getProfessor() != null ? ProfessorResponse.builder()
                                                .id(course.getProfessor().getId())
                                                .code(course.getProfessor().getCode())
                                                .user(UserResponse.builder()
                                                                .id(course.getProfessor().getUser().getId())
                                                                .name(course.getProfessor().getUser().getName())
                                                                .lastName(course.getProfessor().getUser().getLastName())
                                                                .email(course.getProfessor().getUser().getEmail())
                                                                .role(RoleResponse.builder()
                                                                                .id(course.getProfessor().getUser()
                                                                                                .getRole().getId())
                                                                                .name(course.getProfessor().getUser()
                                                                                                .getRole().getName())
                                                                                .description(course.getProfessor()
                                                                                                .getUser().getRole()
                                                                                                .getDescription())
                                                                                .build())
                                                                .verified(course.getProfessor().getUser().isVerified())
                                                                .createdAt(course.getProfessor().getUser()
                                                                                .getCreatedAt())
                                                                .updatedAt(course.getProfessor().getUser()
                                                                                .getUpdatedAt())
                                                                .build())
                                                .build()
                                                : null)
                                .build();
        }

        @Override
        public CourseResponse createCourse(CourseRequest request) {
                SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                                .orElseThrow(() -> new RuntimeException("Subject not found"));
                SemesterEntity semester = semesterRepository.findById(request.getSemesterId())
                                .orElseThrow(() -> new RuntimeException("Semester not found"));
                ShiftEntity shift = shiftRepository.findById(request.getShiftId())
                                .orElseThrow(() -> new RuntimeException("Shift not found"));
                ProfessorEntity professor = professorRepository.findById(request.getProfessorId())
                                .orElseThrow(() -> new RuntimeException("Professor not found"));

                boolean hasConflict = courseRepository.existsByProfessorAndShiftAndSemester(
                                professor, shift, semester);
                if (hasConflict) {
                        throw new RuntimeException("El profesor ya tiene un curso asignado en este turno y semestre");
                }

                CourseEntity course = CourseEntity.builder()
                                .classRoom(request.getClassRoom())
                                .capacity(request.getCapacity())
                                .subject(subject)
                                .semester(semester)
                                .shift(shift)
                                .professor(professor)
                                .build();

                course = courseRepository.save(course);

                return mapToResponse(course);
        }

        @Override
        public CourseResponse getCourseByClassRoom(String classRoom) {
                CourseEntity entity = courseRepository.findByClassRoom(classRoom)
                                .orElseThrow(() -> new RuntimeException("Course not found"));
                return mapToResponse(entity);
        }

        @Override
        public List<CourseResponse> getAllCourses() {
                return courseRepository.findAll()
                                .stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        @Override
        @Transactional
        public CourseResponse updateCourse(Long id, CourseRequest request) {
                CourseEntity course = courseRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Course not found"));

                if (request.getClassRoom() != null) {
                        course.setClassRoom(request.getClassRoom());
                }
                if (request.getCapacity() != null) {
                        course.setCapacity(request.getCapacity());
                }
                if (request.getSubjectId() != null) {
                        course.setSubject(subjectRepository.findById(request.getSubjectId())
                                        .orElseThrow(() -> new RuntimeException("Subject not found")));
                }
                if (request.getSemesterId() != null) {
                        course.setSemester(semesterRepository.findById(request.getSemesterId())
                                        .orElseThrow(() -> new RuntimeException("Semester not found")));
                }
                if (request.getShiftId() != null) {
                        course.setShift(shiftRepository.findById(request.getShiftId())
                                        .orElseThrow(() -> new RuntimeException("Shift not found")));
                }
                if (request.getProfessorId() != null) {
                        course.setProfessor(professorRepository.findById(request.getProfessorId())
                                        .orElseThrow(() -> new RuntimeException("Professor not found")));
                }

                boolean hasConflict = courseRepository.existsByProfessorAndShiftAndSemesterAndIdNot(
                                course.getProfessor(),
                                course.getShift(),
                                course.getSemester(),
                                course.getId());
                if (hasConflict) {
                        throw new RuntimeException("El profesor ya tiene un curso asignado en este turno y semestre");
                }

                CourseEntity updated = courseRepository.save(course);
                return mapToResponse(updated);
        }

        @Override
        public void deleteCourse(Long id) {
                courseRepository.deleteById(id);
        }

        @Override
        public CourseResponse getCourseById(Long id) {
                CourseEntity entity = courseRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("Curso no encontrado con id: " + id));
                return mapToResponse(entity);
        }

}
