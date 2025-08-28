package in.armando.server.service;

import org.springframework.stereotype.Service;

import in.armando.server.entity.StudentEntity;
import in.armando.server.io.students.StudentsRequest;
import in.armando.server.io.students.StudentsResponse;

@Service
public interface StudentsService {
    StudentEntity createStudent(StudentsRequest request);
    StudentsResponse getStudentById(Long id);
    StudentsResponse getStudentByEmail(String email);
}
