package in.armando.server.io.tuitionPayment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TuitionPaymentRequest {
    private Long studentId;
    private Long semesterId;
    private Double tuition;
    private String status;
}
