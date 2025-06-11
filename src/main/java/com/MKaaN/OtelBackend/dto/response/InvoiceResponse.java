package com.MKaaN.OtelBackend.dto.response;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceResponse {
    private String id;
    private String reservationId;
    private String invoiceNumber;
    private BigDecimal amount;
    private String status;
    private LocalDateTime dueDate;
    private LocalDateTime paidAt;
    private String paymentMethod;
    private String notes;
}
