package com.mypayments.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
@Entity
public class Status {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name="CONTRACTOR_ID")
    private Contractor contractor;
    @Column(name = "IS_CONTRACTOR_ON_WL",nullable = false)
    private Boolean isContractorOnWL;
    @Column(name = "IS_BANK_ACCOUNT_ON_WL", nullable = false)
    private Boolean isBankAccountOnWL;
    @Column(name = "STATUS_DATE")
    private LocalDate statusDate;
}
