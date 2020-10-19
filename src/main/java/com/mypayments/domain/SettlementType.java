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
public class SettlementType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "SETTLEMENT_TYPE_ID")
    private Long id;
    @Column(name = "SETTLEMENT_TYPE_NAME")
    private String settlementTypeName;
    @Column(name = "SETTLEMENT_TYPE_CODE")
    private int settlementTypeCode;
    @Column(name ="SPLIT_PAYMENT")
    private int splitPayment;

    @OneToMany(
            targetEntity = Settlement.class,
            mappedBy = "settlementType",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Settlement> settlements = new ArrayList<>();
}
