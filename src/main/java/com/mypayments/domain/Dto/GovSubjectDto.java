package com.mypayments.domain.Dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@ToString
@Getter
@JsonIgnoreProperties(ignoreUnknown =true)
public class GovSubjectDto {

    @JsonProperty("result")
    private ResultDto resultDto;



}
