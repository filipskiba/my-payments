package com.mypayments.domain;

import lombok.*;

import javax.persistence.*;
import javax.websocket.OnError;
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
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SETTLEMENT_ID")
    private Long id;

    @Column(name = "DOCUMENT")
    private String document;

    @ManyToOne
    @JoinColumn(name = "SETTLEMENT_TYPE")
    private SettlementType settlementType;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Contractor owner;

    @ManyToOne
    @JoinColumn(name = "OWNER_BANK_ACCOUNT_ID")
    private BankAccount ownerBankAccount;


    @OneToMany(
            targetEntity = Disposition.class,
            mappedBy = "settlement",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Disposition> dispositions = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @ManyToOne
    @JoinColumn(name = "BANK_ACCOUNT_ID")
    private BankAccount bankAccount;

    @Column(name = "DATE_OF_ISSUE")
    private LocalDate dateOfIssue;

    @Column(name = "DATE_OF_PAYMENT")
    private LocalDate dateOfPayment;

    @Column(name = "AMOUNT")
    private BigDecimal amount;


    // for split payment
    @Builder.Default
    @Column(name = "VAT_AMOUNT")
    private BigDecimal vatAmount = new BigDecimal(0);

    @Column(name = "TITLE")
    private String title;

/*    //for tax payment
    @Column(name = "PERIOD")
    private String period;

    @Column(name = "SIGN")
    private String sign;*/


    @OneToMany(
            targetEntity = Payment.class,
            mappedBy = "settlement",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();
}
