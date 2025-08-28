package in.armando.server.service.impl;

import org.springframework.stereotype.Service;

import in.armando.server.entity.ProfessorEntity;
import in.armando.server.entity.UserEntity;
import in.armando.server.io.professors.ProfessorRequest;
import in.armando.server.io.professors.ProfessorResponse;
import in.armando.server.repository.ProfessorRepositoty;
import in.armando.server.repository.UserRepository;
import in.armando.server.service.ProfessorService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorServiceImpl implements ProfessorService {

    private final ProfessorRepositoty professorRepository;
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

        return new ProfessorResponse(saved.getId(), user.getName());
    }

    @Override
    public ProfessorResponse getProfessorById(Long id) {
        ProfessorEntity professor = professorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));
        return new ProfessorResponse(professor.getId(), professor.getUser().getName());
    }
}
