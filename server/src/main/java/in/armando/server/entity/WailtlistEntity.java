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
@Table(name = "wailtlist")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class WailtlistEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "student_id", nullable = false)
    private StudentEntity studentId;
    @ManyToOne
    @JoinColumn(name = "course_id", nullable = false)
    private CourseEntity courseId;
    private Integer position;
    private LocalDateTime addedDate;
}
