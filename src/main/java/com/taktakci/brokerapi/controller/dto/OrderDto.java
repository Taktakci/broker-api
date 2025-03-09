package com.taktakci.brokerapi.controller.dto;

import com.taktakci.brokerapi.service.model.OrderSide;
import com.taktakci.brokerapi.service.model.OrderStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDto {
    private Integer id;
    @NotNull(message = "customerId cannot be null")
    @Positive(message = "customerId must be a positive number")
    private Integer customerId;
    @NotNull(message = "assetName cannot be null")
    @NotEmpty(message = "assetName cannot be empty")
    private String assetName;
    @NotNull(message = "orderSide cannot be null")
    private OrderSide orderSide;
    @NotNull(message = "size cannot be null")
    @Positive(message = "size must be a positive number")
    private Integer size;
    @NotNull(message = "price cannot be null")
    @Positive(message = "price must be a positive number")
    private Integer price;
    private OrderStatus status;
    private LocalDate createDate;
}
