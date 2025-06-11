package com.MKaaN.OtelBackend.mapper;

import com.MKaaN.OtelBackend.dto.response.PaymentResponse;
import com.MKaaN.OtelBackend.entity.Payment;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    // DTO'dan Entity'ye dönüşüm
    Payment toEntity(PaymentResponse response);

    // Entity'den DTO'ya dönüşüm
    PaymentResponse toResponse(Payment entity);

    // Update işlemleri için
    void updatePayment(PaymentResponse response, @MappingTarget Payment entity);
}
