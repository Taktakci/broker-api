package com.taktakci.brokerapi.controller.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class MatchDto {
    @NotNull(message = "buyOrderId cannot be null")
    @Positive(message = "buyOrderId must be a positive number")
    private Integer buyOrderId;

    @NotNull(message = "sellOrderId cannot be null")
    @Positive(message = "sellOrderId must be a positive number")
    private Integer sellOrderId;
}
