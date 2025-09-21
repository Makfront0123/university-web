package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import in.armando.server.entity.PensumEntity;
import in.armando.server.io.pensum.PensumRequest;
import in.armando.server.io.pensum.PensumResponse;
import in.armando.server.repository.PensumRepository;
import in.armando.server.service.PensumService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PensumServiceImpl implements PensumService {

    private final PensumRepository pensumRepository;

    @Override
    public PensumResponse create(PensumRequest request) {
        if (pensumRepository.existsByName(request.getName())) {
            throw new RuntimeException("Ya existe un pensum con el nombre " + request.getName());
        }

        if (pensumRepository.existsByDescription(request.getDescription())) {
            throw new RuntimeException("Ya existe un pensum con la descripción " + request.getDescription());
        }

        PensumEntity entity = PensumEntity.builder()
                .name(request.getName())
                .description(request.getDescription())
                .build();

        return toResponse(pensumRepository.save(entity));
    }

    @Override
    public PensumResponse update(Long id, PensumRequest request) {
        PensumEntity entity = pensumRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pensum not found"));

        if (request.getName() != null &&
                !request.getName().equals(entity.getName()) &&
                pensumRepository.existsByName(request.getName())) {
            throw new RuntimeException("Ya existe un pensum con el nombre " + request.getName());
        }

        if (request.getDescription() != null &&
                !request.getDescription().equals(entity.getDescription()) &&
                pensumRepository.existsByDescription(request.getDescription())) {
            throw new RuntimeException("Ya existe un pensum con la descripción " + request.getDescription());
        }

        return toResponse(pensumRepository.save(entity));
    }

    @Override
    public void delete(Long id) {
        if (!pensumRepository.existsById(id)) {
            throw new RuntimeException("Pensum not found");
        }
        pensumRepository.deleteById(id);
    }

    @Override
    public PensumResponse getById(Long id) {
        return pensumRepository.findById(id)
                .map(this::toResponse)
                .orElseThrow(() -> new RuntimeException("Pensum not found"));
    }

    @Override
    public List<PensumResponse> getAll() {
        return pensumRepository.findAll().stream()
                .map(this::toResponse)
                .toList();
    }

    private PensumResponse toResponse(PensumEntity entity) {
        return PensumResponse.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())

                .build();
    }
}
