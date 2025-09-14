package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.SubjectEntity;
import in.armando.server.entity.SubjectEquivalenceEntity;
import in.armando.server.io.subjectEquivalence.SubjectEquivalenceRequest;
import in.armando.server.io.subjectEquivalence.SubjectEquivalenceResponse;
import in.armando.server.repository.SubjectEquivalenceRepository;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.SubjectEquivalenceService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectEquivalenceImpl implements SubjectEquivalenceService {
    private final SubjectEquivalenceRepository subjectEquivalenceRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public SubjectEquivalenceResponse addSubjectEquivalence(SubjectEquivalenceRequest request) {
        SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + request.getSubjectId()));

        SubjectEntity equivalentSubject = subjectRepository.findById(request.getEquivalentSubjectId())
                .orElseThrow(() -> new RuntimeException(
                        "Equivalent subject not found with id " + request.getEquivalentSubjectId()));

        SubjectEquivalenceEntity entity = new SubjectEquivalenceEntity();
        entity.setSubject(subject);
        entity.setEquivalentSubject(equivalentSubject);

        SubjectEquivalenceEntity saved = subjectEquivalenceRepository.save(entity);
        return mapToResponse(saved);
    }

    @Override
    public SubjectEquivalenceResponse deleteSubjectEquivalence(Long request) {
        SubjectEquivalenceEntity entity = subjectEquivalenceRepository.findById(request)
                .orElseThrow(() -> new RuntimeException("Equivalence not found with id " + request));

        subjectEquivalenceRepository.delete(entity);

        return mapToResponse(entity);
    }

    @Override
    public SubjectEquivalenceResponse getSubjectEquivalence(Long id) {
        SubjectEquivalenceEntity entity = subjectEquivalenceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equivalence not found with id " + id));

        return SubjectEquivalenceResponse.builder()
                .id(entity.getId())
                .subjectId(entity.getSubject().getId())
                .equivalentSubjectId(entity.getEquivalentSubject().getId())
                .build();
    }

    @Override
    public SubjectEquivalenceResponse getSubjectEquivalences(SubjectEquivalenceRequest request) {
        List<SubjectEquivalenceEntity> entities = subjectEquivalenceRepository.findBySubjectId(request.getSubjectId());
        if (entities.isEmpty()) {
            throw new RuntimeException("No equivalences found for subject id " + request.getSubjectId());
        }

        return mapToResponse(entities.get(0));

    }

    @Override
    public List<SubjectEquivalenceResponse> getSubjectEquivalences() {
        return subjectEquivalenceRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SubjectEquivalenceResponse mapToResponse(SubjectEquivalenceEntity entity) {
        SubjectEquivalenceResponse response = new SubjectEquivalenceResponse();
        response.setId(entity.getId());
        response.setSubjectId(entity.getSubject().getId());
        response.setEquivalentSubjectId(entity.getEquivalentSubject().getId());
        return response;
    }

    @Override
    public SubjectEquivalenceResponse updateSubjectEquivalence(Long id, SubjectEquivalenceRequest request) {
        SubjectEquivalenceEntity entity = subjectEquivalenceRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Equivalence not found with id " + request.getSubjectId()));

        if (request.getSubjectId() != null) {
            SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found with id " + request.getSubjectId()));
            entity.setSubject(subject);
        }

        if (request.getEquivalentSubjectId() != null) {
            SubjectEntity equivalentSubject = subjectRepository.findById(request.getEquivalentSubjectId())
                    .orElseThrow(() -> new RuntimeException(
                            "Equivalent subject not found with id " + request.getEquivalentSubjectId()));
            entity.setEquivalentSubject(equivalentSubject);
        }

        SubjectEquivalenceEntity updated = subjectEquivalenceRepository.save(entity);
        return mapToResponse(updated);
    }

}
