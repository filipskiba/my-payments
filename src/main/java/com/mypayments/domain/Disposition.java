package com.mypayments.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Builder
public class Disposition {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "DISPOSITION_ID")
    private Long id;

    @Column(name = "DATE_OF_EXECUTION")
    private LocalDate dateOfExecution;

    @Column
    private Boolean isExecuted;

    @Column(name = "TITLE")
    private String title;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @Column(name = "VAT_AMOUNT")
    private BigDecimal vatAmount;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Contractor owner;

    @ManyToOne
    @JoinColumn(name = "OWNER_BANKACCOUNT_ID")
    private BankAccount ownerBankAccount;

    @ManyToOne
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @ManyToOne
    @JoinColumn(name = "BANKACCOUNT_ID")
    private BankAccount bankAccount;

    @OneToMany(
            targetEntity = Payment.class,
            mappedBy = "disposition",
            cascade = CascadeType.REMOVE,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();


    @ManyToOne
    @JoinColumn(name = "SETTLEMENT_ID")
    private Settlement settlement;




}
