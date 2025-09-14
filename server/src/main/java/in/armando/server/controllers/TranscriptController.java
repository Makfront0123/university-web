package in.armando.server.controllers;

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
import in.armando.server.io.transcript.TranscriptRequest;
import in.armando.server.io.transcript.TranscriptResponse;
import in.armando.server.service.TranscriptService;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/transcripts")
@RequiredArgsConstructor
public class TranscriptController {

    private final TranscriptService transcriptService;

    @PostMapping
    public ResponseEntity<ApiResponse<TranscriptResponse>> createTranscript(@RequestBody TranscriptRequest request) {
        TranscriptResponse response = transcriptService.createTranscript(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>("Historial académico creado exitosamente", response));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TranscriptResponse>> getTranscriptById(@PathVariable Long id) {
        TranscriptResponse response = transcriptService.getTranscriptById(id);
        return ResponseEntity.ok(new ApiResponse<>("Historial académico encontrado", response));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<ApiResponse<TranscriptResponse>> getTranscriptByStudentId(@PathVariable Long studentId) {
        TranscriptResponse response = transcriptService.getTranscriptByStudentId(studentId);
        return ResponseEntity.ok(new ApiResponse<>("Historial académico del estudiante encontrado", response));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TranscriptResponse>> updateTranscript(
            @PathVariable Long id,
            @RequestBody TranscriptRequest request) {
        TranscriptResponse response = transcriptService.updateTranscript(id, request);
        return ResponseEntity.ok(new ApiResponse<>("Historial académico actualizado exitosamente", response));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> deleteTranscript(@PathVariable Long id) {
        transcriptService.deleteTranscript(id);
        return ResponseEntity.ok(new ApiResponse<>("Historial académico eliminado exitosamente", null));
    }
}
