package in.armando.server.service.impl;

import org.springframework.stereotype.Service;

import in.armando.server.entity.SemesterEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.TranscriptEntity;
import in.armando.server.entity.UserEntity;
import in.armando.server.io.students.StudentsResponse;
import in.armando.server.io.transcript.TranscriptRequest;
import in.armando.server.io.transcript.TranscriptResponse;
import in.armando.server.io.user.UserResponse;
import in.armando.server.repository.SemesterRepository;
import in.armando.server.repository.StudentRepository;
import in.armando.server.repository.TranscriptRepository;
import in.armando.server.service.TranscriptService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TranscriptServiceImpl implements TranscriptService {

    private final TranscriptRepository transcriptRepository;
    private final StudentRepository studentRepository;
    private final SemesterRepository semesterRepository;

    @Override
    public TranscriptResponse createTranscript(TranscriptRequest request) {
        if (request.getEarnedCredits() == null || request.getEarnedCredits() < 0) {
            throw new IllegalArgumentException("Earned credits must be greater or equal to 0");
        }
        if (request.getPromSem() == null || request.getPromSem() < 0 || request.getPromSem() > 5) {
            throw new IllegalArgumentException("Semester GPA must be between 0 and 5");
        }
        if (request.getPromTotal() == null || request.getPromTotal() < 0 || request.getPromTotal() > 5) {
            throw new IllegalArgumentException("Total GPA must be between 0 and 5");
        }

        StudentEntity student = studentRepository.findById(request.getStudentId())
                .orElseThrow(() -> new IllegalArgumentException("Student not found with id " + request.getStudentId()));

        SemesterEntity semester = semesterRepository.findById(request.getSemesterId())
                .orElseThrow(
                        () -> new IllegalArgumentException("Semester not found with id " + request.getSemesterId()));

        boolean exists = transcriptRepository.existsByStudentIdAndSemesterId(student.getId(), semester.getId());
        if (exists) {
            throw new IllegalStateException("Transcript already exists for this student and semester");
        }

        Integer totalCredits = transcriptRepository
                .findByStudentId(student.getId())
                .stream()
                .mapToInt(TranscriptEntity::getEarnedCredits)
                .sum() + request.getEarnedCredits();

        TranscriptEntity transcript = TranscriptEntity.builder()
                .student(student)
                .semester(semester)
                .promSem(request.getPromSem())
                .promTotal(request.getPromTotal())
                .earnedCredits(request.getEarnedCredits())
                .totalCredits(totalCredits)
                .build();

        return toResponse(transcriptRepository.save(transcript));
    }

    @Override
    public TranscriptResponse getTranscriptById(Long id) {
        return transcriptRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Transcript not found with id " + id));
    }

    @Override
    public TranscriptResponse getTranscriptByStudentId(Long studentId) {
        return transcriptRepository.findByStudentId(studentId)
                .stream()
                .findFirst()
                .map(this::toResponse)
                .orElseThrow(() -> new IllegalArgumentException("Transcript not found for student " + studentId));
    }

    @Override
    public void deleteTranscript(Long id) {
        if (!transcriptRepository.existsById(id)) {
            throw new IllegalArgumentException("Transcript not found with id " + id);
        }
        transcriptRepository.deleteById(id);
    }

    @Override
    public TranscriptResponse updateTranscript(Long id, TranscriptRequest request) {
        TranscriptEntity transcript = transcriptRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Transcript not found with id " + id));

        if (request.getPromSem() != null) {
            if (request.getPromSem() < 0 || request.getPromSem() > 5) {
                throw new IllegalArgumentException("Semester GPA must be between 0 and 5");
            }
            transcript.setPromSem(request.getPromSem());
        }

        if (request.getPromTotal() != null) {
            if (request.getPromTotal() < 0 || request.getPromTotal() > 5) {
                throw new IllegalArgumentException("Total GPA must be between 0 and 5");
            }
            transcript.setPromTotal(request.getPromTotal());
        }

        if (request.getEarnedCredits() != null) {
            if (request.getEarnedCredits() < 0) {
                throw new IllegalArgumentException("Earned credits must be greater or equal to 0");
            }
            transcript.setEarnedCredits(request.getEarnedCredits());

            Integer totalCredits = transcriptRepository
                    .findByStudentId(transcript.getStudent().getId())
                    .stream()
                    .filter(t -> !t.getId().equals(id))
                    .mapToInt(TranscriptEntity::getEarnedCredits)
                    .sum() + request.getEarnedCredits();
            transcript.setTotalCredits(totalCredits);
        }

        return toResponse(transcriptRepository.save(transcript));
    }

    private TranscriptResponse toResponse(TranscriptEntity entity) {
        TranscriptResponse response = new TranscriptResponse();
        response.setId(entity.getId());
        response.setPromSem(entity.getPromSem());
        response.setPromTotal(entity.getPromTotal());
        response.setEarnedCredits(entity.getEarnedCredits());
        response.setTotalCredits(entity.getTotalCredits());

        if (entity.getStudent() != null) {
            StudentsResponse studentResponse = new StudentsResponse();
            studentResponse.setUserId(entity.getStudent().getId());
            studentResponse.setCode(entity.getStudent().getCode());

            if (entity.getStudent().getUser() != null) {
                UserEntity user = entity.getStudent().getUser();
                UserResponse userResponse = new UserResponse();
                userResponse.setId(user.getId());
                userResponse.setEmail(user.getEmail());
                userResponse.setName(user.getName());
                userResponse.setLastName(user.getLastName());
                userResponse.setVerified(user.isVerified());

                studentResponse.setUser(userResponse);
            }

            response.setStudent(studentResponse);
        }

        return response;
    }
}
