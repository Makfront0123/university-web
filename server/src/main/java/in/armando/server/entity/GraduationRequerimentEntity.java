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
public class GraduationRequerimentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    private Integer requiredCredits;

    private Boolean mandatory;

    @Column(name = "mandatory_credits", nullable = false)
    private Integer mandatoryCredits;  

    @ManyToOne
    @JoinColumn(name = "pensum_id", nullable = false)
    private PensumEntity pensum;
}
