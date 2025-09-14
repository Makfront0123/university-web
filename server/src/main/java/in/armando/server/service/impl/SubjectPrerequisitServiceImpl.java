package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.SubjectEntity;
import in.armando.server.entity.SubjectPrerequisisitEntity;
import in.armando.server.io.subjectPrerequisit.SubjectPrerequisitRequest;
import in.armando.server.io.subjectPrerequisit.SubjectPrerequisitResponse;
import in.armando.server.repository.SubjectPrerequisitRepository;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.SubjectPrerequisitService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SubjectPrerequisitServiceImpl implements SubjectPrerequisitService {
    final SubjectPrerequisitRepository subjectPrerequisitRepository;
    final SubjectRepository subjectRepository;

    @Override
    public SubjectPrerequisitResponse addSubjectPrerequisit(SubjectPrerequisitRequest request) {
        if (request.getSubjectId() == null || request.getRequiredSubjectId() == null) {
            throw new IllegalArgumentException("subjectId and requiredSubjectId must not be null");
        }
        SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found with id " + request.getSubjectId()));

        SubjectEntity requiredSubject = subjectRepository.findById(request.getRequiredSubjectId())
                .orElseThrow(() -> new RuntimeException(
                        "Required subject not found with id " + request.getRequiredSubjectId()));

        SubjectPrerequisisitEntity entity = new SubjectPrerequisisitEntity();
        entity.setSubject(subject);
        entity.setRequiredSubject(requiredSubject);

        SubjectPrerequisisitEntity saved = subjectPrerequisitRepository.save(entity);

        return new SubjectPrerequisitResponse(
                saved.getId(),
                saved.getSubject().getId(),
                saved.getRequiredSubject().getId());
    }

    @Override
    public SubjectPrerequisitResponse deleteSubjectPrerequisit(Long id) {
        SubjectPrerequisisitEntity entity = subjectPrerequisitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Prerequisit not found with id " + id));

        SubjectPrerequisitResponse response = mapToResponse(entity);

        subjectPrerequisitRepository.deleteById(id);

        return response;
    }

    @Override
    public SubjectPrerequisitResponse getSubjectPrerequisit(Long id) {
        SubjectPrerequisisitEntity entity = subjectPrerequisitRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Equivalence not found with id " + id));

        return mapToResponse(entity);
    }

    @Override
    public List<SubjectPrerequisitResponse> getSubjectPrerequisits() {
        return subjectPrerequisitRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private SubjectPrerequisitResponse mapToResponse(SubjectPrerequisisitEntity entity) {
        return SubjectPrerequisitResponse.builder()
                .id(entity.getId())
                .subjectId(entity.getSubject().getId())
                .requiredSubjectId(entity.getRequiredSubject().getId())
                .build();
    }

    @Override
    public SubjectPrerequisitResponse updateSubjectPrerequisit(Long id, SubjectPrerequisitRequest request) {
        SubjectPrerequisisitEntity entity = subjectPrerequisitRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Equivalence not found with id " + request.getSubjectId()));

        if (request.getSubjectId() != null) {
            SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found with id " + request.getSubjectId()));
            entity.setSubject(subject);
        }

        if (request.getRequiredSubjectId() != null) {
            SubjectEntity requiredSubject = subjectRepository.findById(request.getRequiredSubjectId())
                    .orElseThrow(() -> new RuntimeException(
                            "Required subject not found with id " + request.getRequiredSubjectId()));
            entity.setRequiredSubject(requiredSubject);
        }

        SubjectPrerequisisitEntity updated = subjectPrerequisitRepository.save(entity);
        return mapToResponse(updated);
    }

}
