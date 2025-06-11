package com.MKaaN.OtelBackend.mapper;

import com.MKaaN.OtelBackend.dto.response.InvoiceResponse;
import com.MKaaN.OtelBackend.entity.Invoice;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface InvoiceMapper {
    // DTO'dan Entity'ye dönüşüm
    Invoice toEntity(InvoiceResponse dto);

    // Entity'den DTO'ya dönüşüm
    InvoiceResponse toDTO(Invoice entity);

    // Update işlemleri için
    void updateInvoice(InvoiceResponse dto, @MappingTarget Invoice entity);
}
