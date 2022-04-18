package application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name = "type_incomes")
public class TypeIncome {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "incomes_sequence_generator")
    @SequenceGenerator(name = "incomes_sequence_generator", sequenceName = "incomes_sequence", allocationSize = 1)
    private Long id;

    @Column
    private String name;
}
