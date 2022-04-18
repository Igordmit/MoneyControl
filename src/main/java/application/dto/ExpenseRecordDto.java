package application.dto;

import lombok.Builder;
import lombok.Value;

import java.util.Date;

@Builder
@Value
public class ExpenseRecordDto {

    Integer errorCode;

    Double accountValue;

    Double price;

    String nameTypeExpense;

    String comment;

    Date date;
}
