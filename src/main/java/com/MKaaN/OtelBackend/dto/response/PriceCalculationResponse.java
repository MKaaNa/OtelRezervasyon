package com.MKaaN.OtelBackend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PriceCalculationResponse {
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal dailyPrice;
    private BigDecimal totalPrice;
    private int numberOfDays;}
 