package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.GraduationRequerimentEntity;
import in.armando.server.entity.PensumEntity;
import in.armando.server.io.graduationRequeriment.GraduationRequerimentRequest;
import in.armando.server.io.graduationRequeriment.GraduationRequerimentResponse;
import in.armando.server.repository.GraduationRequerimentRepository;
import in.armando.server.repository.PensumRepository;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.GraduationRequerimentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GraduationRequerimentServiceImpl implements GraduationRequerimentService {
    private final GraduationRequerimentRepository repository;
    private final PensumRepository pensumRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public GraduationRequerimentResponse create(GraduationRequerimentRequest request) {
        PensumEntity pensum = pensumRepository.findById(request.getPensumId())
                .orElseThrow(() -> new RuntimeException("Pensum not found"));

        if (repository.existsByNameAndPensumId(request.getName(), pensum.getId())) {
            throw new RuntimeException("Ya existe una relación con el nombre " + request.getName());
        }

        Integer totalCredits = subjectRepository.sumCreditsByPensumId(pensum.getId());
        if (totalCredits == null) {
            totalCredits = 0;
        }

        if (request.getMandatoryCredits() != null && request.getMandatoryCredits() > totalCredits) {
            throw new RuntimeException("El requerimiento de graduación no puede ser mayor a los créditos del pensum ("
                    + totalCredits + ")");
        }

        GraduationRequerimentEntity entity = GraduationRequerimentEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .requiredCredits(request.getRequiredCredits())
                .mandatory(request.getMandatory())
                .mandatoryCredits(request.getMandatoryCredits())
                .pensum(pensum)
                .build();

        repository.save(entity);

        return mapToResponse(entity);
    }

    @Override
    public GraduationRequerimentResponse getById(Long id) {
        return repository.findById(id)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Relation not found"));
    }

    @Override
    public void delete(Long id) {
        if (!repository.existsById(id)) {
            throw new RuntimeException("Relation not found");
        }
        repository.deleteById(id);
    }

    @Override
    public List<GraduationRequerimentResponse> getAll() {
        return repository.findAll().stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public GraduationRequerimentResponse update(Long id, GraduationRequerimentRequest request) {
        GraduationRequerimentEntity entity = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        if (request.getName() != null) {
            entity.setName(request.getName());
        }
        if (request.getDescription() != null) {
            entity.setDescription(request.getDescription());
        }
        if (request.getRequiredCredits() != null) {
            entity.setRequiredCredits(request.getRequiredCredits());
        }
        if (request.getMandatory() != null) {
            entity.setMandatory(request.getMandatory());
        }
        if (request.getMandatoryCredits() != null) {
            entity.setMandatoryCredits(request.getMandatoryCredits());
        }

        repository.save(entity);

        return mapToResponse(entity);
    }

    private GraduationRequerimentResponse mapToResponse(GraduationRequerimentEntity entity) {
        return GraduationRequerimentResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .requiredCredits(entity.getRequiredCredits())
                .mandatory(entity.getMandatory())
                .pensumId(entity.getPensum().getId())
                .build();
    }

}
