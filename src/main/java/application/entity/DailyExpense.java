package application.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Entity
@Table(name="daily_expenses")
@Builder
public class DailyExpense {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "daily_expenses_sequence_generator")
    @SequenceGenerator(name = "daily_expenses_sequence_generator", sequenceName = "daily_expenses_sequence", allocationSize = 1)
    @Column
    private Long id;

    @Column
    private Date date;

    @ManyToOne(fetch = FetchType.EAGER)
    private TypeExpense type;

    @Column
    private Double summ;

    @Column
    private String comment;
}
