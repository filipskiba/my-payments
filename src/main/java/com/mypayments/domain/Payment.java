package com.mypayments.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "PAYMENT_ID")
    private Long id;

    @Column(name = "PAYMENT_TYPE")
    private String paymentType;

    @ManyToOne
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @Column(name = "DATE_OF_TRANSFER")
    private LocalDate dateOfTranfer;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "VAT")
    private BigDecimal vat;

    @Column(name = "SPLIT_PAYMENT",nullable = false)
    private Boolean isSplitPayment;

    @ManyToOne
    @JoinColumn(name = "SETTLEMENT_ID")
    private Settlement settlement;


}
