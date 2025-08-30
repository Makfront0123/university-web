package in.armando.server.service;

import java.util.List;

import in.armando.server.io.shift.ShiftRequest;
import in.armando.server.io.shift.ShiftResponse;

public interface ShiftService {
    ShiftResponse createShift(ShiftRequest request);

    ShiftResponse getShiftById(Long id);

    ShiftResponse updateShift(Long id, ShiftRequest request);

    ShiftResponse deleteShift(Long id);

    List<ShiftResponse> getAllShifts();

}
