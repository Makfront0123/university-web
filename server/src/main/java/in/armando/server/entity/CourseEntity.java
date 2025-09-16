package in.armando.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@Table(name = "courses")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CourseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subjectId", nullable = false)
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "semesterId", nullable = false)
    private SemesterEntity semester;

    @ManyToOne
    @JoinColumn(name = "shiftId", nullable = false)
    private ShiftEntity shift;

    @ManyToOne
    @JoinColumn(name = "professorId", nullable = false)
    private ProfessorEntity professor;

    @Column(nullable = false)
    private String classRoom;

    @Column(nullable = false)
    private Integer capacity;
}
