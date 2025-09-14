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
import in.armando.server.io.pensum.PensumRequest;
import in.armando.server.io.pensum.PensumResponse;
import in.armando.server.service.PensumService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/pensum")
@RequiredArgsConstructor
public class PensumController {

    private final PensumService pensumService;

    @PostMapping
    public ResponseEntity<ApiResponse<PensumResponse>> createPensum(@RequestBody PensumRequest request) {
        PensumResponse response = pensumService.create(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Pensum creado exitosamente", response));
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<PensumResponse>>> getAllPensum() {
        List<PensumResponse> response = pensumService.getAll();
        return ResponseEntity.ok(new ApiResponse<>("Lista de pensums", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<PensumResponse>> getPensumById(@PathVariable Long id) {
        PensumResponse response = pensumService.getById(id);
        return ResponseEntity.ok(new ApiResponse<>("Pensum encontrado", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deletePensum(@PathVariable Long id) {
        pensumService.delete(id);
        return ResponseEntity.ok(new ApiResponse<>("Pensum eliminado", null));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<PensumResponse>> updatePensum(@PathVariable Long id,
                                                                    @RequestBody PensumRequest request) {
        PensumResponse response = pensumService.update(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Pensum actualizado", response));
    }
}
