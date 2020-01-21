package com.mypayments.domain.Dto;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown =true)
public class GovSubjectDto {
    @JsonProperty("name")
    private String name;
    @JsonProperty("nip")
    private String nip;
    @JsonProperty("statusVat")
    private String statusVat;
    @JsonProperty("regon")
    private String regon;


}
