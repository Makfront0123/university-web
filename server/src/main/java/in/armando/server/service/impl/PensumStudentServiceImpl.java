package in.armando.server.service.impl;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.PensumEntity;
import in.armando.server.entity.PensumStudentEntity;
import in.armando.server.entity.StudentEntity;
import in.armando.server.entity.TuitionPaymentEntity;
import in.armando.server.io.pensumStudent.PensumStudentRequest;
import in.armando.server.io.pensumStudent.PensumStudentResponse;
import in.armando.server.repository.PensumRepository;
import in.armando.server.repository.PensumStudentRepository;
import in.armando.server.repository.StudentRepository;
import in.armando.server.repository.TuitionPaymentRepository;
import in.armando.server.service.PensumStudentService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensumStudentServiceImpl implements PensumStudentService {
        private final PensumStudentRepository repository;
        private final StudentRepository studentRepository;
        private final PensumRepository pensumRepository;
        private final TuitionPaymentRepository tuitionPaymentRepository;

        @Override
        public PensumStudentResponse createPensumStudent(PensumStudentRequest request) {
                StudentEntity student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new RuntimeException("Student not found"));
                PensumEntity pensum = pensumRepository.findById(request.getPensumId())
                                .orElseThrow(() -> new RuntimeException("Pensum not found"));
                TuitionPaymentEntity assignedBy = tuitionPaymentRepository.findById(request.getAssignedBy())
                                .orElseThrow(() -> new RuntimeException("TuitionPayment not found"));

              
                if (repository.existsByStudentAndPensum(student, pensum)) {
                        throw new RuntimeException("El estudiante ya está asignado a este pensum");
                }

                PensumStudentEntity entity = PensumStudentEntity.builder()
                                .student(student)
                                .pensum(pensum)
                                .assignedBy(assignedBy)
                                .createdAt(LocalDateTime.now())
                                .updatedAt(LocalDateTime.now())
                                .build();

                entity = repository.save(entity);
                return mapToResponse(entity);
        }

        @Override
        public PensumStudentResponse getPensumStudentById(Long id) {
                PensumStudentEntity entity = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException("PensumStudent not found"));
                return mapToResponse(entity);
        }

        @Override
        public void deletePensumStudent(Long id) {
                if (!repository.existsById(id)) {
                        throw new RuntimeException("PensumStudent not found");
                }
                repository.deleteById(id);
        }

        @Override
        public PensumStudentResponse updatePensumStudent(Long id, PensumStudentRequest request) {
                PensumStudentEntity entity = repository.findById(id)
                                .orElseThrow(() -> new RuntimeException("PensumStudent not found"));

                StudentEntity student = studentRepository.findById(request.getStudentId())
                                .orElseThrow(() -> new RuntimeException("Student not found"));
                PensumEntity pensum = pensumRepository.findById(request.getPensumId())
                                .orElseThrow(() -> new RuntimeException("Pensum not found"));
                TuitionPaymentEntity assignedBy = tuitionPaymentRepository.findById(request.getAssignedBy())
                                .orElseThrow(() -> new RuntimeException("TuitionPayment not found"));

                if (repository.existsByStudentAndPensum(student, pensum)
                                && !entity.getStudent().equals(student)) {
                        throw new RuntimeException("El estudiante ya está asignado a este pensum");
                }

                entity.setStudent(student);
                entity.setPensum(pensum);
                entity.setAssignedBy(assignedBy);
                entity.setUpdatedAt(LocalDateTime.now());

                repository.save(entity);
                return mapToResponse(entity);
        }

        @Override
        public List<PensumStudentResponse> getAll() {
                return repository.findAll().stream()
                                .map(this::mapToResponse)
                                .toList();
        }

        private PensumStudentResponse mapToResponse(PensumStudentEntity entity) {
                return PensumStudentResponse.builder()
                                .id(entity.getId())
                                .pensumId(entity.getPensum().getId())
                                .studentId(entity.getStudent().getId())
                                .assignedBy(entity.getAssignedBy().getId())
                                .createdAt(entity.getCreatedAt())
                                .updatedAt(entity.getUpdatedAt())
                                .build();
        }
}