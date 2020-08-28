package com.mypayments.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@ToString
@Entity
public class BankAccount {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column(name = "BANK_ACCOUNT")
    private String accountNumber;

    @ManyToOne
    @JoinColumn(name="CONTRACTOR_ID")
    private Contractor contractor;

    @OneToMany(
            targetEntity = Settlement.class,
            mappedBy = "bankAccount",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Settlement> settlements = new ArrayList<>();

    @OneToMany(
            targetEntity = Settlement.class,
            mappedBy = "ownerBankAccount",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Settlement> settlementsByOwnerAccount = new ArrayList<>();

    @OneToMany(
            targetEntity = Payment.class,
            mappedBy = "ownerBankAccount",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> paymentsByOwnerAccount = new ArrayList<>();

    @OneToMany(
            targetEntity = Payment.class,
            mappedBy = "bankAccount",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(
            targetEntity = Disposition.class,
            mappedBy = "bankAccount",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Disposition> dispositions = new ArrayList<>();

    @OneToMany(
            targetEntity = Disposition.class,
            mappedBy = "ownerBankAccount",
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Disposition> ownerDispositions = new ArrayList<>();
}
