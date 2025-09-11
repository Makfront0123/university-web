package in.armando.server.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tuition_payment")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TuitionPaymentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "semesterId", referencedColumnName = "id")
    private SemesterEntity semester;

    @Column(nullable = false)
    private Double tuition;

    @Column(nullable = false)
    private String status; 

    @Column(nullable = false)
    private LocalDateTime paymentDate;
}
