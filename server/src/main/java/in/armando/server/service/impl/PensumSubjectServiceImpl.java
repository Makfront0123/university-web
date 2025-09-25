package in.armando.server.service.impl;

import java.util.List;
import org.springframework.stereotype.Service;

import in.armando.server.entity.PensumEntity;
import in.armando.server.entity.PensumSubjectEntity;
import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.SubjectEntity;
import in.armando.server.io.pensum.PensumResponse;
import in.armando.server.io.pensumSubject.PensumSubjectRequest;
import in.armando.server.io.pensumSubject.PensumSubjectResponse;
import in.armando.server.io.semester.SemesterResponse;
import in.armando.server.io.subject.SubjectResponse;
import in.armando.server.repository.PensumRepository;
import in.armando.server.repository.PensumSubjectRepository;
import in.armando.server.repository.SemesterRepository;
import in.armando.server.repository.SubjectRepository;
import in.armando.server.service.PensumSubjectService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensumSubjectServiceImpl implements PensumSubjectService {

    private final PensumSubjectRepository pensumSubjectRepository;
    private final PensumRepository pensumRepository;
    private final SubjectRepository subjectRepository;
    private final SemesterRepository semesterRepository;

    @Override
    public PensumSubjectResponse create(PensumSubjectRequest request) {
        PensumEntity pensum = pensumRepository.findById(request.getPensumId())
                .orElseThrow(() -> new RuntimeException("Pensum not found"));

        SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                .orElseThrow(() -> new RuntimeException("Subject not found"));

        SemesterEntity semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(() -> new RuntimeException("Semester not found"));

        if (pensumSubjectRepository.existsByPensumAndSubject(pensum, subject)) {
            throw new RuntimeException("La asignatura ya está asociada a este pensum");
        }

        PensumSubjectEntity entity = PensumSubjectEntity.builder()
                .pensum(pensum)
                .subject(subject)
                .semester(semester)
                .build();

        PensumSubjectEntity saved = pensumSubjectRepository.save(entity);

        PensumSubjectResponse response = toResponse(saved);
        response.setMessage("Asociación creada con éxito");
        return response;
    }

    @Override
    public List<PensumSubjectResponse> getAll() {
        return pensumSubjectRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    @Override
    public PensumSubjectResponse getById(Long id) {
        PensumSubjectEntity entity = pensumSubjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relation not found"));
        return toResponse(entity);
    }

    @Override
    public void delete(Long id) {
        if (!pensumSubjectRepository.existsById(id)) {
            throw new RuntimeException("Relation not found");
        }
        pensumSubjectRepository.deleteById(id);
    }

    @Override
    public PensumSubjectResponse update(Long id, PensumSubjectRequest request) {
        PensumSubjectEntity entity = pensumSubjectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Relation not found"));

        if (request.getPensumId() != null) {
            PensumEntity pensum = pensumRepository.findById(request.getPensumId())
                    .orElseThrow(() -> new RuntimeException("Pensum not found"));
            entity.setPensum(pensum);
        }

        if (request.getSubjectId() != null) {
            SubjectEntity subject = subjectRepository.findById(request.getSubjectId())
                    .orElseThrow(() -> new RuntimeException("Subject not found"));
            entity.setSubject(subject);
        }

        if (request.getSemesterId() != null) {
            SemesterEntity semester = semesterRepository.findById(request.getSemesterId())
                    .orElseThrow(() -> new RuntimeException("Semester not found"));
            entity.setSemester(semester);
        }

        PensumSubjectEntity updated = pensumSubjectRepository.save(entity);

        PensumSubjectResponse response = toResponse(updated);
        response.setMessage("Asociación actualizada con éxito");
        return response;
    }

    private PensumSubjectResponse toResponse(PensumSubjectEntity entity) {
        return PensumSubjectResponse.builder()
                .id(entity.getId())
                .pensumId(PensumResponse.builder()
                        .id(entity.getPensum().getId())
                        .name(entity.getPensum().getName())
                        .description(entity.getPensum().getDescription())
                        .build())
                .subjectId(SubjectResponse.builder()
                        .id(entity.getSubject().getId())
                        .name(entity.getSubject().getName())
                        .credits(entity.getSubject().getCredits())
                        .build())
                .semesterId(SemesterResponse.builder()
                        .id(entity.getSemester().getId())
                        .name(entity.getSemester().getName())
                        .build())
                .build();
    }

}
