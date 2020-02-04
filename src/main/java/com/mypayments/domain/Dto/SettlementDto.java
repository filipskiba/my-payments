package com.mypayments.domain.Dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class SettlementDto {
    private Long settlementId;
    private String document;
    private Long contractorId;
    private String contractorName;
    private LocalDate dateOfIssue;
    private LocalDate dateOfPayment;
    private BigDecimal amount;
    private List<PaymentDto> paymentDtoList;
    private Boolean isPaid;
    private BigDecimal paidAmount;

}
