package in.armando.server.service;

import in.armando.server.io.transcript.TranscriptRequest;
import in.armando.server.io.transcript.TranscriptResponse;


public interface TranscriptService {
    TranscriptResponse createTranscript(TranscriptRequest transcript);

    TranscriptResponse getTranscriptById(Long id);

    TranscriptResponse getTranscriptByStudentId(Long studentId);

    void deleteTranscript(Long id);

    TranscriptResponse updateTranscript(Long id, TranscriptRequest transcript);
}
