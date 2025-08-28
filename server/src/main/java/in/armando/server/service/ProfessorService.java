package in.armando.server.service;

import org.springframework.stereotype.Service;

import in.armando.server.io.professors.ProfessorRequest;
import in.armando.server.io.professors.ProfessorResponse;

@Service
public interface ProfessorService {
    ProfessorResponse createProfessor(ProfessorRequest request);

    ProfessorResponse getProfessorById(Long id);
}
