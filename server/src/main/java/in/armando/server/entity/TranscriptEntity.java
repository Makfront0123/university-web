package in.armando.server.entity;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

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
@Table(name = "transcript")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TranscriptEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentEntity studentId;

    @ManyToOne
    @JoinColumn(name = "semesterId", referencedColumnName = "id")
    private SemesterEntity semesterId;

    @Column(nullable = false, unique = true)
    private Double promSem;

    @Column(nullable = false, unique = true)
    private Double promTotal;

    @Column(nullable = false, unique = true)
    private Integer earnedCredits;

    @Column(nullable = false, unique = true)
    private Integer totalCredits;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdAt;
    @UpdateTimestamp
    @Column(updatable = false)
    private LocalDateTime updatedAt;
}
