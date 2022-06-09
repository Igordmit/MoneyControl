package application.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "type_expenses")
public class TypeExpense{

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "expenses_sequence_generator")
    @SequenceGenerator(name = "expenses_sequence_generator", sequenceName = "expenses_sequence", allocationSize = 1)
    @Column
    private Long id;

    @Column
    private String name;

}
