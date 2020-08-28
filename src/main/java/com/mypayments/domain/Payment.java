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

    @ManyToOne
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @Column(name = "DOCUMENT")
    private String document;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Contractor owner;

    @ManyToOne
    @JoinColumn(name = "OWNER_BANKACCOUNT_ID")
    private BankAccount ownerBankAccount;

    @Column(name = "DATE_OF_TRANSFER")
    private LocalDate dateOfTranfer;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @ManyToOne
    @JoinColumn(name = "BANKACCOUNT_ID")
    private BankAccount bankAccount;

    @ManyToOne
    @JoinColumn(name = "SETTLEMENT_ID")
    private Settlement settlement;

    @ManyToOne
    @JoinColumn(name = "DISPOSITION_ID")
    private Disposition disposition;


}
