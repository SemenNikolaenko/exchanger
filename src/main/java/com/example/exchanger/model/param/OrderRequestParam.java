package com.example.exchanger.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class OrderRequestParam {
    private String exchangeAction;
    private String convertFrom;
    private double exchangeRate;
    private String convertTo;
    private double amount;

}
