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
@Table(name = "enrollments")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EnrollmentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "courseId", referencedColumnName = "id")
    private CourseEntity courseId;

    @ManyToOne
    @JoinColumn(name = "studentId", referencedColumnName = "id")
    private StudentEntity studentId;

    @Column(nullable = false, unique = true)
    private LocalDateTime enrollmentDate;

    @Column(nullable = false, unique = true)
    private Integer grade;

    @Column(nullable = false, unique = true)
    private String status;
}
