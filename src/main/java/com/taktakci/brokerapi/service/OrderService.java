package com.taktakci.brokerapi.service;

import com.taktakci.brokerapi.exception.BrokerException;
import com.taktakci.brokerapi.repository.OrderRepository;
import com.taktakci.brokerapi.repository.entity.Order;
import com.taktakci.brokerapi.service.model.OrderSide;
import com.taktakci.brokerapi.service.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static com.taktakci.brokerapi.service.AssetService.ASSET_TRY;

@Service
@Slf4j
public class OrderService {


    private final OrderRepository orderRepository;
    private final AssetService assetService;

    public OrderService(OrderRepository orderRepository, AssetService assetService) {
        this.orderRepository = orderRepository;
        this.assetService = assetService;
    }

    public List<Order> listOrders(Integer customerId, LocalDate fromDate, LocalDate toDate) {
        return orderRepository.findByCustomerIdAndCreateDateGreaterThanEqualAndCreateDateLessThanEqual(
                customerId, fromDate, toDate);
    }

    public Order createOrder(Order order) {

        if (order.getAssetName().equals(ASSET_TRY)) {
            throw new BrokerException("you cannot buy or sell TRY");
        }

        if (order.getOrderSide().equals(OrderSide.BUY)) {
            assetService.decreaseUsableSize(order.getCustomerId(), ASSET_TRY, order.getPrice() * order.getSize());
        } else if (order.getOrderSide().equals(OrderSide.SELL)) {
            assetService.decreaseUsableSize(order.getCustomerId(), order.getAssetName(), order.getSize());
        }

        order.setStatus(OrderStatus.PENDING);
        order.setCreateDate(LocalDate.now());
        order = orderRepository.save(order);
        return order;
    }

    public void deleteOrder(Integer id) {
        Optional<Order> orderOptional = orderRepository.findById(id);

        if (orderOptional.isEmpty()) {
            throw new BrokerException("Order not found!");
        }

        Order order = orderOptional.get();
        if (!order.getStatus().equals(OrderStatus.PENDING)) {
            throw new BrokerException("The status of the Order to be deleted must be PENDING!");
        }

        if (order.getOrderSide().equals(OrderSide.BUY)) {
            assetService.increaseUsableSize(order.getCustomerId(), ASSET_TRY, order.getSize() * order.getPrice());
        } else if (order.getOrderSide().equals(OrderSide.SELL)) {
            assetService.increaseUsableSize(order.getCustomerId(), order.getAssetName(), order.getSize());
        }

        order.setStatus(OrderStatus.CANCELED);
        orderRepository.save(order);
    }




}
