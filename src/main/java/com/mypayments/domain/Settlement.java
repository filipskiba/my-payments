package com.mypayments.domain;

import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.MathContext;
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
    @JoinColumn(name = "CONTRACTOR_ID")
    private Contractor contractor;

    @Column(name = "DATE_OF_ISSUE")
    private LocalDate dateOfIssue;

    @Column(name = "DATE_OF_PAYMENT")
    private LocalDate dateOfPayment;

    @Column(name = "CURRENCY")
    private String currency;

    @Column(name = "AMOUNT")
    private BigDecimal amount;

    @OneToMany(
            targetEntity = Payment.class,
            mappedBy = "settlement",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY
    )
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();




}
