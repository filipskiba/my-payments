package com.mypayments.domain.Dto;

import lombok.*;

import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@Builder
public class DispositionsPackageDto {
    private String dateOfExecution;
    private List<Long> idsList;
}
