package in.armando.server.service;

import java.util.List;

import in.armando.server.io.course.CourseRequest;
import in.armando.server.io.course.CourseResponse;

public interface CourseService {
    CourseResponse createCourse(CourseRequest course);
    CourseResponse getCourseById(Long id);
    CourseResponse getCourseByCode(String code);
    List<CourseResponse> getAllCourses();
    CourseResponse updateCourse(Long id, CourseRequest course);
    void deleteCourse(Long id);
}
