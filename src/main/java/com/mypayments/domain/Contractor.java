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
@Entity
public class Contractor {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONTRACTOR_ID")
    private Long id;
    @Column(name = "CONTRACTOR_NAME")
    private String contractorName;
    @Column(name="NIP_ID")
    private String nipId;
    @Column(name="REGON_ID")
    private String regonId;
    @Column(name="PESEL_ID")
    private String peselId;

    @OneToMany(
            targetEntity = BankAccount.class,
            mappedBy = "contractor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(
            targetEntity = Status.class,
            mappedBy = "contractor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Status> statuses = new ArrayList<>();

    @OneToMany(
            targetEntity = Payment.class,
            mappedBy = "contractor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Payment> payments = new ArrayList<>();

    @OneToMany(
            targetEntity = Settlement.class,
            mappedBy = "contractor",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Settlement> settlements = new ArrayList<>();





}
