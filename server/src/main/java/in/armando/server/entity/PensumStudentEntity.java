package in.armando.server.entity;

import java.time.LocalDateTime;

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
@Table(name = "student_pensum")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PensumStudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    private StudentEntity student;

    @ManyToOne
    @JoinColumn(name = "pensum_id", referencedColumnName = "id")
    private PensumEntity pensum;

    @ManyToOne
    @JoinColumn(name = "assigned_by", referencedColumnName = "id")
    private TuitionPaymentEntity assignedBy;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}