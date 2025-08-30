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
import in.armando.server.repository.CourseRepository;
import in.armando.server.repository.ProfessorRepository;
import in.armando.server.repository.SemesterRepository;
import in.armando.server.repository.ShiftRepository;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.CourseService;
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
    public CourseResponse updateCourse(Long id, CourseRequest course) {
        CourseEntity entity = courseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Course not found"));

        entity.setClassRoom(course.getClassRoom());
        entity.setCapacity(course.getCapacity());

        courseRepository.save(entity);
        return mapToResponse(entity);
    }

    @Override
    public void deleteCourse(Long id) {
        courseRepository.deleteById(id);
    }

}
