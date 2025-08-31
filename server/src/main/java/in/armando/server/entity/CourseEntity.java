package in.armando.server.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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

    @OneToOne
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    private SubjectEntity subject;

    @OneToOne
    @JoinColumn(name = "semesterId", referencedColumnName = "id")
    private SemesterEntity semester;

    @OneToOne
    @JoinColumn(name = "shiftId", referencedColumnName = "id")
    private ShiftEntity shift;

    @OneToOne
    @JoinColumn(name = "professorId", referencedColumnName = "id")
    private ProfessorEntity professor;

    @Column(nullable = false)
    private String classRoom;

    @Column(nullable = false)
    private Integer capacity;

}
