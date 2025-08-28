package in.armando.server.service.impl;

import org.springframework.stereotype.Service;

import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.UserEntity;
import in.armando.server.io.students.StudentsRequest;
import in.armando.server.io.students.StudentsResponse;
import in.armando.server.repository.StudentRepository;
import in.armando.server.repository.UserRepository;
import in.armando.server.service.StudentsService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentsService {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;

    @Override
    public StudentEntity createStudent(StudentsRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        StudentEntity student = new StudentEntity();
        student.setUser(user);

        String code = "STU-" + System.currentTimeMillis();
        student.setCode(code);

        return studentRepository.save(student);
    }

    @Override
    public StudentsResponse getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Student no encontrado"));
        return new StudentsResponse(student.getId(), student.getCode());
    }

    @Override
    public StudentsResponse getStudentByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        StudentEntity student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student no encontrado"));
        return new StudentsResponse(student.getId(), student.getCode());
    }
}
