package in.armando.server.io.tuitionPayment;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TuitionPaymentResponse {
    private Long id;
    private Long studentId;
    private Long semesterId;
    private Double tuition;
    private String status;
    private LocalDateTime paymentDate;
}
