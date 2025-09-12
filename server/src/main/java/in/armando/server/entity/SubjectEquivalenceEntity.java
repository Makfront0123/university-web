package in.armando.server.entity;

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
@Table(name = "subject_equivalence")
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEquivalenceEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "subjectId", referencedColumnName = "id")
    private SubjectEntity subject;

    @ManyToOne
    @JoinColumn(name = "equivalentSubjectId", referencedColumnName = "id")
    private SubjectEntity equivalentSubject;
}