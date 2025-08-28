package in.armando.server.service;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.io.professors.ProfessorRequest;
import in.armando.server.io.professors.ProfessorResponse;

@Service
public interface ProfessorService {
    ProfessorResponse createProfessor(ProfessorRequest request);

    ProfessorResponse updateProfessor(Long id, ProfessorRequest request);

    List<ProfessorResponse> getAllProfessors();

    ProfessorResponse getProfessorById(Long id);

    ProfessorResponse getProfessorByCode(String code);

    void deleteProfessor(Long id);
}
