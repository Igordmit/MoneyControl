package application.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="daily_incomes")
@Builder
public class DailyIncome {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_incomes_sequence_generator")
    @SequenceGenerator(name = "daily_incomes_sequence_generator", sequenceName = "daily_incomes_sequence", allocationSize = 1)
    @Column
    private Long id;

    @Column
    private Date date;

    @ManyToOne(fetch = FetchType.LAZY)
    private TypeIncome type;

    @Column
    private Double summ;

    @Column
    private String comment;
}
