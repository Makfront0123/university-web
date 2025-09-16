package in.armando.server.service;

import java.util.List;

import in.armando.server.io.course.CourseRequest;
import in.armando.server.io.course.CourseResponse;

public interface CourseService {
    CourseResponse createCourse(CourseRequest request);
    CourseResponse getCourseById(Long id);
    List<CourseResponse> getAllCourses();
    CourseResponse getCourseByClassRoom(String classRoom);
    CourseResponse updateCourse(Long id, CourseRequest request);
    void deleteCourse(Long id);
    
}
