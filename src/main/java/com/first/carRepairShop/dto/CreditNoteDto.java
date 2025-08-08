package com.first.carRepairShop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.first.carRepairShop.entity.Invoice;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreditNoteDto {
    private Integer id;
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String creditNoteNumber;

    private Integer originalInvoiceId;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private LocalDate issuedDate;
    private BigDecimal amount; // Amount being refunded/adjusted (should be positive)

    private String reason; // Optional explanation

}
