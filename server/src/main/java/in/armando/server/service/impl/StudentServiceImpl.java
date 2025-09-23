package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.UserEntity;
import in.armando.server.io.role.RoleResponse;
import in.armando.server.io.students.StudentsRequest;
import in.armando.server.io.students.StudentsResponse;
import in.armando.server.io.user.UserResponse;
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
    public StudentsResponse getStudentByEmail(String email) {
        UserEntity user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        StudentEntity student = studentRepository.findByUser(user)
                .orElseThrow(() -> new RuntimeException("Student no encontrado"));
        return new StudentsResponse(student.getId(), student.getCode(), mapToUserResponse(user));
    }

    private UserResponse mapToUserResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setVerified(user.isVerified());
        response.setRole(
                RoleResponse.builder()
                        .id(user.getRole().getId())
                        .name(user.getRole().getName())
                        .build());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

    @Override
    public StudentsResponse createStudent(StudentsRequest request) {
        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!"STUDENTS".equalsIgnoreCase(user.getRole().getName())) {
            throw new RuntimeException("El usuario no tiene rol STUDENTS");
        }

        if (!user.isVerified()) {
            throw new RuntimeException("El usuario no ha sido verificado");
        }

        if (studentRepository.findByUser(user).isPresent()) {
            throw new RuntimeException("ya existe un estudiante asignado a este usuario");
        }
        StudentEntity student = new StudentEntity();
        student.setUser(user);

        String code = "STU-" + System.currentTimeMillis();
        if (studentRepository.findByCode(code).isPresent()) {
            throw new RuntimeException("ya existe un estudiante con el mismo código");
        }
        student.setCode(code);

        StudentEntity savedStudent = studentRepository.save(student);

        return convertToResponse(savedStudent);
    }

    @Override
    public List<StudentsResponse> getAllStudents() {
        return studentRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public StudentsResponse getStudentById(Long id) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));
        return convertToResponse(student);
    }

    @Override
    public StudentsResponse getStudentByCode(String code) {
        StudentEntity student = studentRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con código: " + code));
        return convertToResponse(student);
    }

    private StudentsResponse convertToResponse(StudentEntity entity) {
        return new StudentsResponse(entity.getId(), entity.getCode(), mapToUserResponse(entity.getUser()));
    }

    @Override
    public StudentsResponse updateStudent(Long id, StudentsRequest request) {
        StudentEntity student = studentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado"));

        if (request.getCode() != null && !request.getCode().isBlank()) {
            student.setCode(request.getCode());
        }

        UserEntity user = student.getUser();

        if (request.getUser() != null) {
            if (request.getUser().getName() != null)
                user.setName(request.getUser().getName());
            if (request.getUser().getLastName() != null)
                user.setLastName(request.getUser().getLastName());
            if (request.getUser().getEmail() != null)
                user.setEmail(request.getUser().getEmail());
        }

        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getLastName() != null)
            user.setLastName(request.getLastName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        userRepository.save(user);
        StudentEntity updated = studentRepository.save(student);

        return convertToResponse(updated);
    }

    @Override
    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

}
