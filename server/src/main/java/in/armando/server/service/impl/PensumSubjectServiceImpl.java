package in.armando.server.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import in.armando.server.entity.PensumEntity;
import in.armando.server.entity.PensumSubjectEntity;
import in.armando.server.entity.SubjectEntity;
import in.armando.server.io.pensum.PensumResponse;
import in.armando.server.io.pensumSubject.PensumSubjectRequest;
import in.armando.server.io.pensumSubject.PensumSubjectResponse;
import in.armando.server.io.subject.SubjectResponse;
import in.armando.server.repository.PensumRepository;
import in.armando.server.repository.PensumSubjectRepository;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.PensumSubjectService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensumSubjectServiceImpl implements PensumSubjectService {

    private final PensumSubjectRepository pensumSubjectRepository;
    private final PensumRepository pensumRepository;
    private final SubjectRepository subjectRepository;

    @Override
    public PensumSubjectResponse create(PensumSubjectRequest request) {
        Optional<PensumEntity> pensumOpt = pensumRepository.findById(request.getPensumId());
        if (pensumOpt.isEmpty()) {
            return PensumSubjectResponse.builder()
                    .message("Pensum not found")
                    .build();
        }

        Optional<SubjectEntity> subjectOpt = subjectRepository.findById(request.getSubjectId());
        if (subjectOpt.isEmpty()) {
            return PensumSubjectResponse.builder()
                    .message("Subject not found")
                    .build();
        }

        PensumEntity pensum = pensumOpt.get();
        SubjectEntity subject = subjectOpt.get();

        PensumSubjectEntity entity = PensumSubjectEntity.builder()
                .pensumId(pensum)
                .subjectId(subject)
                .build();

        return toResponse(pensumSubjectRepository.save(entity));
    }

    @Override
    public List<PensumSubjectResponse> getAll() {
        return pensumSubjectRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PensumSubjectResponse getById(Long id) {
        return pensumSubjectRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Relation not found"));
    }

    @Override
    public void delete(Long id) {
        if (!pensumSubjectRepository.existsById(id)) {
            throw new RuntimeException("Relation not found");
        }
        pensumSubjectRepository.deleteById(id);
    }

    private PensumSubjectResponse toResponse(PensumSubjectEntity entity) {
        return PensumSubjectResponse.builder()
                .id(entity.getId())
                .pensumId(PensumResponse.builder()
                        .id(entity.getPensumId().getId())
                        .name(entity.getPensumId().getName())
                        .description(entity.getPensumId().getDescription())
                        .build())
                .subjectId(SubjectResponse.builder()
                        .id(entity.getSubjectId().getId())
                        .name(entity.getSubjectId().getName())
                        .credits(entity.getSubjectId().getCredits())
                        .build())
                .message("Asociación creada con éxito")
                .build();
    }

    @Override
    public PensumSubjectResponse update(Long id, PensumSubjectRequest request) {
        Optional<PensumSubjectEntity> entityOpt = pensumSubjectRepository.findById(id);
        if (entityOpt.isEmpty()) {
            return PensumSubjectResponse.builder()
                    .message("Relation not found")
                    .build();
        }

        PensumSubjectEntity entity = entityOpt.get();

        if (request.getPensumId() != null) {
            Optional<PensumEntity> pensumOpt = pensumRepository.findById(request.getPensumId());
            if (pensumOpt.isEmpty()) {
                return PensumSubjectResponse.builder()
                        .message("Pensum not found")
                        .build();
            }
            entity.setPensumId(pensumOpt.get());
        }

        if (request.getSubjectId() != null) {
            Optional<SubjectEntity> subjectOpt = subjectRepository.findById(request.getSubjectId());
            if (subjectOpt.isEmpty()) {
                return PensumSubjectResponse.builder()
                        .message("Subject not found")
                        .build();
            }
            entity.setSubjectId(subjectOpt.get());
        }

        PensumSubjectEntity updated = pensumSubjectRepository.save(entity);

        PensumSubjectResponse response = toResponse(updated);
        response.setMessage("Asociación actualizada con éxito");
        return response;
    }

}
