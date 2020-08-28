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
public class ContractorType {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CONTRACTORTYPE_ID")
    private Long id;
    @Column(name = "CONTRACTORTYPE_NAME")
    private String contractorTypeName;

    @OneToMany(
            targetEntity = Contractor.class,
            mappedBy = "contractorType",
            cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @Builder.Default
    private List<Contractor> contractors = new ArrayList<>();
}
