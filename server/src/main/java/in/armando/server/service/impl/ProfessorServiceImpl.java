package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.ProfessorEntity;
import in.armando.server.entity.UserEntity;
import in.armando.server.io.professors.ProfessorRequest;
import in.armando.server.io.professors.ProfessorResponse;
import in.armando.server.io.user.UserResponse;
import in.armando.server.repository.ProfessorRepository;
import in.armando.server.repository.UserRepository;
import in.armando.server.service.ProfessorService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepository professorRepository;
    private final UserRepository userRepository;

    @Override
    public ProfessorResponse createProfessor(ProfessorRequest request) {

        UserEntity user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!"TEACHERS".equalsIgnoreCase(user.getRole().getName())) {
            throw new RuntimeException("El usuario no tiene rol TEACHERS");
        }

        String professorCode = "PROF-" + user.getId();

        ProfessorEntity professor = ProfessorEntity.builder()
                .code(professorCode)
                .user(user)
                .build();

        ProfessorEntity saved = professorRepository.save(professor);

        return convertToResponse(saved);
    }

    @Override
    public ProfessorResponse getProfessorById(Long id) {
        ProfessorEntity professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        return convertToResponse(professor);
    }

    @Override
    public ProfessorResponse updateProfessor(Long id, ProfessorRequest request) {
        ProfessorEntity professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        if (request.getCode() != null && !request.getCode().isBlank()) {
            professor.setCode(request.getCode());
        }

        UserEntity user = professor.getUser();

        if (request.getName() != null)
            user.setName(request.getName());
        if (request.getLastName() != null)
            user.setLastName(request.getLastName());
        if (request.getEmail() != null)
            user.setEmail(request.getEmail());

        userRepository.save(user);
        ProfessorEntity updated = professorRepository.save(professor);
        return convertToResponse(updated);
    }

    @Override
    public List<ProfessorResponse> getAllProfessors() {
        return professorRepository.findAll()
                .stream()
                .map(this::convertToResponse)
                .toList();
    }

    @Override
    public ProfessorResponse getProfessorByCode(String code) {
        ProfessorEntity professor = professorRepository.findByCode(code)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado con código: " + code));
        return convertToResponse(professor);
    }

    @Override
    public void deleteProfessor(Long id) {
        professorRepository.deleteById(id);
    }

    private ProfessorResponse convertToResponse(ProfessorEntity entity) {
        return new ProfessorResponse(entity.getId(), entity.getCode(), mapToUserResponse(entity.getUser()));
    }

    private UserResponse mapToUserResponse(UserEntity user) {
        UserResponse response = new UserResponse();
        response.setId(user.getId());
        response.setName(user.getName());
        response.setEmail(user.getEmail());
        response.setVerified(user.isVerified());
        response.setRole(user.getRole().getName());
        response.setCreatedAt(user.getCreatedAt());
        response.setUpdatedAt(user.getUpdatedAt());
        return response;
    }

}
