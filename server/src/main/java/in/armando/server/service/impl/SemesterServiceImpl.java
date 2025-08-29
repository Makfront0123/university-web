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
        return new SemesterResponse(
                semester.getId(),
                semester.getName(),
                semester.getStartDate(),
                semester.getEndDate());
    }

    @Override
    public SemesterResponse createSemester(SemesterRequest semesterRequest) {
        SemesterEntity semesterEntity = SemesterEntity.builder()
                .name(semesterRequest.getName())
                .startDate(semesterRequest.getStartDate())
                .endDate(semesterRequest.getEndDate())
                .build();
        semesterRepository.save(semesterEntity);
        return mapToResponse(semesterEntity);
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
        semester.setName(semesterRequest.getName());
        semester.setStartDate(semesterRequest.getStartDate());
        semester.setEndDate(semesterRequest.getEndDate());
        semesterRepository.save(semester);
        return mapToResponse(semester);
    }

    @Override
    public void deleteSemester(Long id) {
        semesterRepository.deleteById(id);
    }

    @Override
    public List<SemesterResponse> getAllSemester() {
        return semesterRepository.findAll().stream().map(this::mapToResponse).toList();
    }

}
