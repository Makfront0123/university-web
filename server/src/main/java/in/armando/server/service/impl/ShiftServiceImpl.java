package in.armando.server.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import in.armando.server.entity.ShiftEntity;

import in.armando.server.io.shift.ShiftRequest;
import in.armando.server.io.shift.ShiftResponse;
import in.armando.server.repository.ShiftRepository;
import in.armando.server.service.ShiftService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShiftServiceImpl implements ShiftService {
    private final ShiftRepository shiftRepository;

    private ShiftResponse mapToResponse(ShiftEntity shift) {
        return mapToResponse(shift, null);
    }

    private ShiftResponse mapToResponse(ShiftEntity shift, String message) {
        return new ShiftResponse(
                shift.getId(),
                shift.getName(),
                shift.getStartTime(),
                shift.getEndTime(),
                shift.getDayOfWeek(),
                message);
    }

    private ShiftEntity mapToEntity(ShiftRequest shiftRequest) {
        return ShiftEntity.builder()
                .name(shiftRequest.getName())
                .startTime(shiftRequest.getStartTime())
                .endTime(shiftRequest.getEndTime())
                .dayOfWeek(shiftRequest.getDayOfWeek())
                .build();
    }

    @Override
    public ShiftResponse createShift(ShiftRequest request) {
        ShiftEntity shift = mapToEntity(request);
        ShiftEntity savedShift = shiftRepository.save(shift);
        return mapToResponse(savedShift, "Shift creado correctamente");
    }

    @Override
    public ShiftResponse getShiftById(Long id) {
        ShiftEntity shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift no encontrado con id: " + id));
        return mapToResponse(shift);
    }

    @Override
    public ShiftResponse updateShift(Long id, ShiftRequest request) {
        ShiftEntity shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift no encontrado con id: " + id));

        if (request.getName() != null)
            shift.setName(request.getName());
        if (request.getStartTime() != null)
            shift.setStartTime(request.getStartTime());
        if (request.getEndTime() != null)
            shift.setEndTime(request.getEndTime());
        if (request.getDayOfWeek() != null)
            shift.setDayOfWeek(request.getDayOfWeek());

        shiftRepository.save(shift);
        return mapToResponse(shift, "Shift actualizado correctamente");
    }

    @Override
    public ShiftResponse deleteShift(Long id) {
        ShiftEntity shift = shiftRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Shift no encontrado con id: " + id));
        shiftRepository.deleteById(id);
        return mapToResponse(shift, "Shift eliminado correctamente");
    }

    @Override
    public List<ShiftResponse> getAllShifts() {
        return shiftRepository.findAll()
                .stream()
                .map(shift -> mapToResponse(shift))
                .toList();
    }
}
