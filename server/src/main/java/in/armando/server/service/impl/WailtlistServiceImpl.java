package in.armando.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.CourseEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.WailtlistEntity;
import in.armando.server.io.waitlist.WaitlistRequest;
import in.armando.server.io.waitlist.WaitlistResponse;
import in.armando.server.repository.CourseRepository;
import in.armando.server.repository.StudentRepository;
import in.armando.server.repository.WaitlistRepository;
import in.armando.server.service.WaitlistService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class WailtlistServiceImpl implements WaitlistService {

    private final WaitlistRepository waitlistRepository;
    private final StudentRepository studentsRepository;
    private final CourseRepository courseRepository;

    @Override
    public WaitlistResponse create(WaitlistRequest request) {

        StudentEntity student = studentsRepository.findById(request.getStudentId())
                .orElseThrow(() -> new RuntimeException("Student not found"));

        CourseEntity course = courseRepository.findById(request.getCourseId())
                .orElseThrow(() -> new RuntimeException("Course not found"));

        WailtlistEntity entity = WailtlistEntity.builder()
                .studentId(student)
                .courseId(course)
                .position(request.getPosition())
                .addedDate(LocalDateTime.now())
                .build();

        WailtlistEntity saved = waitlistRepository.save(entity);

        return WaitlistResponse.builder()
                .id(saved.getId())
                .studentId(saved.getStudentId().getId())
                .courseId(saved.getCourseId().getId())
                .position(saved.getPosition())
                .message("Asociación creada con éxito")
                .addedDate(saved.getAddedDate())
                .build();
    }

    private WaitlistResponse toResponse(WailtlistEntity entity) {
        return WaitlistResponse.builder()
                .id(entity.getId())
                .studentId(entity.getStudentId().getId())
                .courseId(entity.getCourseId().getId())
                .position(entity.getPosition())
                .message("Asociación creada con éxito")
                .addedDate(entity.getAddedDate())
                .build();
    }

    @Override
    public WaitlistResponse getById(Long id) {
        WailtlistEntity entity = waitlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Waitlist no encontrada"));
        return toResponse(entity);
    }

    @Override
    public WaitlistResponse update(Long id, WaitlistRequest request) {
        WailtlistEntity entity = waitlistRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Waitlist no encontrada"));

        if (request.getStudentId() != null) {
            StudentEntity student = studentsRepository.findById(request.getStudentId())
                    .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
            entity.setStudentId(student);
        }

        if (request.getCourseId() != null) {
            CourseEntity course = courseRepository.findById(request.getCourseId())
                    .orElseThrow(() -> new RuntimeException("Curso no encontrado"));
            entity.setCourseId(course);
        }

        if (request.getPosition() != null) {
            entity.setPosition(request.getPosition());
        }

        WailtlistEntity updated = waitlistRepository.save(entity);

        return toResponse(updated);
    }

    @Override
    public List<WaitlistResponse> getAll() {
        return waitlistRepository.findAll()
                .stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public WaitlistResponse delete(Long id) {
        waitlistRepository.deleteById(id);
        return toResponse(waitlistRepository.findById(id).get());
    }
}
