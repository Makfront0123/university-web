package in.armando.server.io.transcript;

import lombok.Data;

@Data
public class TranscriptRequest {
    private Double promSem;
    private Double promTotal;
    private Integer earnedCredits;
    private Long semesterId;
    private Long studentId;
}
