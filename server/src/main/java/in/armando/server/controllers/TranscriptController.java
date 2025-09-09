package in.armando.server.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<TranscriptResponse> createTranscript(@RequestBody TranscriptRequest request) {
        return ResponseEntity.ok(transcriptService.createTranscript(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TranscriptResponse> getTranscriptById(@PathVariable Long id) {
        return ResponseEntity.ok(transcriptService.getTranscriptById(id));
    }

    @GetMapping("/student/{studentId}")
    public ResponseEntity<TranscriptResponse> getTranscriptByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(transcriptService.getTranscriptByStudentId(studentId));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TranscriptResponse> updateTranscript(
            @PathVariable Long id,
            @RequestBody TranscriptRequest request) {
        return ResponseEntity.ok(transcriptService.updateTranscript(id, request));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTranscript(@PathVariable Long id) {
        transcriptService.deleteTranscript(id);
        return ResponseEntity.noContent().build();
    }
}
