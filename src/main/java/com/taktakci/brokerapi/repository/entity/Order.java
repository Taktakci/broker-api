package com.taktakci.brokerapi.repository.entity;

import com.taktakci.brokerapi.service.model.OrderSide;
import com.taktakci.brokerapi.service.model.OrderStatus;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "STOCK_ORDER")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Order {
    @Id
    @Column(name = "ID", nullable = false)
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;

    @Column(name="CUSTOMER_ID")
    private Integer customerId;

    @Column(name="ASSET_NAME")
    private String assetName;

    @Column(name="ORDER_SIDE")
    @Enumerated(EnumType.STRING)
    private OrderSide orderSide;

    @Column(name="SIZE")
    private Integer size;

    @Column(name="PRICE")
    private Integer price;

    @Column(name="STATUS")
    @Enumerated(EnumType.STRING)
    private OrderStatus status;

    @Column(name="CREATE_DATE")
    private LocalDate createDate;
}
