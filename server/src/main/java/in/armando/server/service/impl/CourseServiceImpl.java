package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.CourseEntity;
import in.armando.server.io.course.CourseRequest;
import in.armando.server.io.course.CourseResponse;
import in.armando.server.repository.CourseRepository;
import in.armando.server.service.CourseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CourseServiceImpl implements CourseService {
    private final CourseRepository courseRepository;

    private CourseResponse mapToResponse(CourseEntity course) {
        return CourseResponse.builder()
                .id(course.getId())
                .classRoom(course.getClassRoom())
                .capacity(course.getCapacity())
                .build();
    }

    @Override
    public CourseResponse createCourse(CourseRequest course) {
        CourseEntity courseEntity = CourseEntity.builder()
                .classRoom(course.getClassRoom())
                .capacity(course.getCapacity())
                .build();
        courseRepository.save(courseEntity);
        return mapToResponse(courseEntity);
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
