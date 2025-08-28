package in.armando.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.io.students.StudentsRequest;
import in.armando.server.io.students.StudentsResponse;

@Service
public interface StudentsService {
    StudentsResponse createStudent(StudentsRequest request);

    StudentsResponse getStudentById(Long id);

    StudentsResponse getStudentByEmail(String email);

    StudentsResponse getStudentByCode(String code);

    StudentsResponse updateStudent(Long id, StudentsRequest request);

    void deleteStudent(Long id);

    List<StudentsResponse> getAllStudents();
}
