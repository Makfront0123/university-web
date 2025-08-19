package in.armando.server.entity;

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
@Table(name = "graduation_reqquirement")
@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GraduationReqquirementEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String name;

    @Column(nullable = false, unique = true)
    private String description;

    @Column(nullable = false, unique = true)
    private String requiredCredits;

    @Column(nullable = false, unique = true)
    private String mandatoryCredits;

    @ManyToOne
    @JoinColumn(name = "pensumId", referencedColumnName = "id")
    private PensumEntity pensumId;
}
