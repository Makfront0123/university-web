package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.SemesterEntity;
import in.armando.server.io.semester.SemesterRequest;
import in.armando.server.io.semester.SemesterResponse;
import in.armando.server.repository.SemesterRepository;
import in.armando.server.service.SemesterService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SemesterServiceImpl implements SemesterService {
    private final SemesterRepository semesterRepository;

    private SemesterResponse mapToResponse(SemesterEntity semester) {
        return mapToResponse(semester, null);
    }

    private SemesterResponse mapToResponse(SemesterEntity semester, String message) {
        return new SemesterResponse(
                semester.getId(),
                semester.getName(),
                semester.getStartDate(),
                semester.getEndDate(),
                message);
    }

    private SemesterEntity mapToEntity(SemesterRequest semesterRequest) {
        return SemesterEntity.builder()
                .name(semesterRequest.getName())
                .startDate(semesterRequest.getStartDate())
                .endDate(semesterRequest.getEndDate())
                .build();
    }

    @Override
    public SemesterResponse createSemester(SemesterRequest semesterRequest) {
        if (semesterRequest.getName() == null || semesterRequest.getName().isBlank()) {
            throw new RuntimeException("El nombre del semestre no puede estar en blanco");
        }
        if (semesterRequest.getStartDate() == null) {
            throw new RuntimeException("La fecha de inicio del semestre no puede estar en blanco");
        }
        if (semesterRequest.getEndDate() == null) {
            throw new RuntimeException("La fecha de fin del semestre no puede estar en blanco");
        }
        semesterRepository.findByName(semesterRequest.getName())
                .ifPresent(s -> {
                    throw new RuntimeException("Ya existe un semestre con el mismo nombre");
                });

        if (semesterRepository.findByDateRange(semesterRequest.getStartDate(), semesterRequest.getEndDate())
                .isPresent()) {
            throw new RuntimeException("Ya existe un semestre con la misma fecha de inicio y fin");
        }

        SemesterEntity semester = mapToEntity(semesterRequest);
        SemesterEntity savedSemester = semesterRepository.save(semester);
        SemesterResponse response = mapToResponse(savedSemester);
        response.setMessage("Semestre creado correctamente");
        return response;
    }

    @Override
    public SemesterResponse getSemesterById(Long id) {
        SemesterEntity semester = semesterRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Semester no encontrado con id: " + id));
        return mapToResponse(semester);
    }

    @Override
    public SemesterResponse updateSemester(Long id, SemesterRequest semesterRequest) {
        SemesterEntity semester = semesterRepository.findById(id).orElseThrow(
                () -> new RuntimeException("Semester no encontrado con id: " + id));

        if (semesterRequest.getName() != null) {
            semester.setName(semesterRequest.getName());
        }
        if (semesterRequest.getStartDate() != null) {
            semester.setStartDate(semesterRequest.getStartDate());
        }
        if (semesterRequest.getEndDate() != null) {
            semester.setEndDate(semesterRequest.getEndDate());
        }

        semesterRepository.save(semester);

        SemesterResponse response = mapToResponse(semester);
        response.setMessage("Semestre actualizado correctamente");
        return response;
    }

    @Override
    public SemesterResponse deleteSemester(Long id) {
        SemesterResponse response = getSemesterById(id);
        semesterRepository.deleteById(id);
        response.setMessage("Semestre eliminado correctamente");
        return response;
    }

    @Override
    public List<SemesterResponse> getAllSemester() {
        return semesterRepository.findAll().stream().map(this::mapToResponse).toList();
    }

}
