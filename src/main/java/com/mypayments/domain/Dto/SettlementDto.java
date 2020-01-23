package com.mypayments.domain.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SettlementDto {
    private String document;
    private Long contractorId;
    private String dateOfIssue;
    private String dateOfPayment;
    private BigDecimal amount;
    private String currency;
    private List<PaymentDto> paymentDtoList;
    private Boolean isPaid;
    private BigDecimal paidAmount;

}
