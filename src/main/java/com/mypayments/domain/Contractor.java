package com.mypayments.domain;

import lombok.*;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

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


    @OneToMany(
            orphanRemoval = true,
            targetEntity = BankAccount.class,
            mappedBy = "contractor",
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
    @Builder.Default
    private List<BankAccount> bankAccounts = new ArrayList<>();

    @OneToMany(
            targetEntity = Status.class,
            mappedBy = "contractor",
            orphanRemoval = true,
            fetch = FetchType.EAGER)
    @Fetch(FetchMode.SELECT)
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
            orphanRemoval = true,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Settlement> settlements = new ArrayList<>();





}
