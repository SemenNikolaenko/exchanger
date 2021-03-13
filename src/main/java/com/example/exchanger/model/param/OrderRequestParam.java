package com.example.exchanger.model.param;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestParam {
    private String exchangeAction;
    private String convertFrom;
    private double exchangeRate;
    private String convertTo;
    private double amount;

}
