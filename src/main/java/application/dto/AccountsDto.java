package application.dto;

import lombok.Builder;

@Builder
public class AccountsDto {
    String errorName;

    Long idfrom;

    String nameFrom;

    Double valueFrom;

    Long idto;

    String nameto;

    Double valueto;
}
