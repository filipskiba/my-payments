package com.mypayments.domain;

import lombok.*;

import javax.persistence.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
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
}
