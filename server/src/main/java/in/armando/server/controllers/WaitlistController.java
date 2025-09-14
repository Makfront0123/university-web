package in.armando.server.controllers;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.armando.server.io.ApiResponse;
import in.armando.server.io.waitlist.WaitlistRequest;
import in.armando.server.io.waitlist.WaitlistResponse;
import in.armando.server.service.WaitlistService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/waitlist")
@RequiredArgsConstructor
public class WaitlistController {

    private final WaitlistService waitlistService;

    @PostMapping
    public ResponseEntity<ApiResponse<WaitlistResponse>> createWaitlist(@RequestBody WaitlistRequest request) {
        WaitlistResponse response = waitlistService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Estudiante agregado a la lista de espera exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<WaitlistResponse>> getWaitlistById(@PathVariable Long id) {
        WaitlistResponse response = waitlistService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>("Lista de espera encontrada", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<WaitlistResponse>>> getAllWaitlists() {
        List<WaitlistResponse> response = waitlistService.getAll();
        return ResponseEntity.ok(new ApiResponse<>("Lista completa de espera", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<WaitlistResponse>> updateWaitlist(
            @PathVariable Long id,
            @RequestBody WaitlistRequest request) {
        WaitlistResponse response = waitlistService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Lista de espera actualizada exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteWaitlist(@PathVariable Long id) {
        waitlistService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Estudiante eliminado de la lista de espera", null));
    }
}
