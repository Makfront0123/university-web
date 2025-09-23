package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.SubjectEntity;
import in.armando.server.io.subject.SubjectRequest;
import in.armando.server.io.subject.SubjectResponse;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.SubjectService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectServiceImpl implements SubjectService {
    private final SubjectRepository subjectRepository;

    private SubjectEntity mapToEntity(SubjectRequest subjectRequest) {
        return SubjectEntity.builder()
                .code(subjectRequest.getCode())
                .name(subjectRequest.getName())
                .credits(subjectRequest.getCredits())
                .build();
    }

    private SubjectResponse mapToResponse(SubjectEntity subjectEntity) {
        return SubjectResponse.builder()
                .id(subjectEntity.getId())
                .code(subjectEntity.getCode())
                .name(subjectEntity.getName())
                .credits(subjectEntity.getCredits())
                .build();
    }

    @Override
    public SubjectResponse createSubject(SubjectRequest request) {
        if (request.getCode() == null || request.getCode().isBlank()) {
            throw new RuntimeException("Code cannot be null or blank");
        }
        if (subjectRepository.findByCode(request.getCode()) != null) {
            throw new RuntimeException("Subject with code " + request.getCode() + " already exists");
        }
        if (request.getName() == null || request.getName().isBlank()) {
            throw new RuntimeException("Name cannot be null or blank");
        }
        if (request.getCredits() == null || request.getCredits() <= 0) {
            throw new RuntimeException("Credits cannot be null");
        }
        SubjectEntity subjectEntity = mapToEntity(request);
        SubjectEntity savedSubject = subjectRepository.save(subjectEntity);
        SubjectResponse response = mapToResponse(savedSubject);
        response.setMessage("Subject created successfully");
        return response;
    }

    @Override
    public SubjectResponse getSubjectById(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));
        return mapToResponse(subject);
    }

    @Override
    public SubjectResponse getSubjectByCode(String code) {
        SubjectEntity subject = subjectRepository.findByCode(code);
        if (subject == null) {
            throw new RuntimeException("Subject not found with code " + code);
        }
        return mapToResponse(subject);
    }

    @Override
    public List<SubjectResponse> getAllSubjects() {
        return subjectRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public SubjectResponse updateSubject(Long id, SubjectRequest request) {
        SubjectEntity subjectEntity = subjectRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Subject not found with id " + id));

        if (request.getCode() != null && !request.getCode().isBlank()) {
            SubjectEntity existing = subjectRepository.findByCode(request.getCode());
            if (existing != null && !existing.getId().equals(id)) {
                throw new IllegalStateException("Another subject with code " + request.getCode() + " already exists");
            }
            subjectEntity.setCode(request.getCode());
        }
        if (request.getName() != null && !request.getName().isBlank()) {
            subjectEntity.setName(request.getName());
        }
        if (request.getCredits() != null) {
            if (request.getCredits() <= 0) {
                throw new IllegalArgumentException("Subject credits must be greater than 0");
            }
            subjectEntity.setCredits(request.getCredits());
        }

        subjectRepository.save(subjectEntity);
        SubjectResponse response = mapToResponse(subjectEntity);
        response.setMessage("Subject updated successfully");
        return response;
    }

    @Override
    public SubjectResponse deleteSubject(Long id) {
        SubjectEntity subject = subjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + id));
        subjectRepository.deleteById(id);
        SubjectResponse response = mapToResponse(subject);
        response.setMessage("Subject deleted successfully");
        return response;
    }
}
