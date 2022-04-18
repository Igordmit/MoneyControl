package application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name="accounts")
@Builder
@Data
public class Account {

    @Column
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "accounts_sequence_generator")
    @SequenceGenerator(name = "accounts_sequence_generator", sequenceName = "accounts_sequence", allocationSize = 1)
    private Long id;

    @Column
    private String name;

    @Column
    private Boolean mainFlag;

    @Column
    private Double value;
}
