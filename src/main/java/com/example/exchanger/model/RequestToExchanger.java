package com.example.exchanger.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class RequestToExchanger {
    private Long timestamp;
    private String base;
    @JsonProperty("rates")
    private Map<String, Double> currency;
}
