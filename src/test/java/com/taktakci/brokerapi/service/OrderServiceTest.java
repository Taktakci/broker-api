package com.taktakci.brokerapi.service;

import com.taktakci.brokerapi.exception.BrokerException;
import com.taktakci.brokerapi.repository.OrderRepository;
import com.taktakci.brokerapi.repository.entity.Order;
import com.taktakci.brokerapi.service.model.OrderSide;
import com.taktakci.brokerapi.service.model.OrderStatus;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@SpringBootTest
class OrderServiceTest {

    @Mock
    private OrderRepository mockedOrderRepository;

    @Mock
    private AssetService mockedAssetService;

    @Test
    void testCreateOrder_CreatesBuyOrderSuccessfully() {
        OrderService orderService = new OrderService(mockedOrderRepository, mockedAssetService);
        Order order = new Order(null, 123, "asset1", OrderSide.BUY, 5, 12, null, null);
        when(mockedOrderRepository.save(order))
                .thenReturn(order);

        Order savedOrder = orderService.createOrder(order);

        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
        assertNotNull(savedOrder.getCreateDate());
        assertEquals(123, savedOrder.getCustomerId());
        assertEquals(OrderSide.BUY, savedOrder.getOrderSide());
    }

    @Test
    void testCreateOrder_CreatesSellOrderSuccessfully() {
        OrderService orderService = new OrderService(mockedOrderRepository, mockedAssetService);
        Order order = new Order(null, 123, "asset1", OrderSide.SELL, 5, 12, null, null);
        when(mockedOrderRepository.save(order))
                .thenReturn(order);

        Order savedOrder = orderService.createOrder(order);

        assertEquals(OrderStatus.PENDING, savedOrder.getStatus());
        assertNotNull(savedOrder.getCreateDate());
        assertEquals(123, savedOrder.getCustomerId());
        assertEquals(OrderSide.SELL, savedOrder.getOrderSide());
    }

    @Test
    void testCreateOrder_ThrowsExceptionIfAssetIsTRY() {
        OrderService orderService = new OrderService(mockedOrderRepository, mockedAssetService);
        Order order = new Order(null, 123, AssetService.ASSET_TRY, OrderSide.SELL, 5, 12, null, null);

        assertThrows(BrokerException.class, () -> orderService.createOrder(order));
    }

    @Test
    void testDeleteOrder_DeletesBuyOrderSuccessfully() {
        OrderService orderService = new OrderService(mockedOrderRepository, mockedAssetService);
        Order order = new Order(5, 123, "asset1", OrderSide.BUY, 5, 12, OrderStatus.PENDING, LocalDate.now());
        when(mockedOrderRepository.findById(5))
                .thenReturn(Optional.of(order));

        orderService.deleteOrder(5);

        assertEquals(OrderStatus.CANCELED, order.getStatus());
        assertEquals(OrderSide.BUY, order.getOrderSide());
    }

    @Test
    void testDeleteOrder_DeletesSellOrderSuccessfully() {
        OrderService orderService = new OrderService(mockedOrderRepository, mockedAssetService);
        Order order = new Order(5, 123, "asset1", OrderSide.SELL, 5, 12, OrderStatus.PENDING, LocalDate.now());
        when(mockedOrderRepository.findById(5))
                .thenReturn(Optional.of(order));

        orderService.deleteOrder(5);

        assertEquals(OrderStatus.CANCELED, order.getStatus());
        assertEquals(OrderSide.SELL, order.getOrderSide());
    }

    @Test
    void testDeleteOrder_ThrowsExceptionIfStatusNotPending() {
        OrderService orderService = new OrderService(mockedOrderRepository, mockedAssetService);
        Order order = new Order(5, 123, "asset1", OrderSide.SELL, 5, 12, OrderStatus.CANCELED, LocalDate.now());
        when(mockedOrderRepository.findById(5))
                .thenReturn(Optional.of(order));

        assertThrows(BrokerException.class, () -> orderService.deleteOrder(5));
    }

}