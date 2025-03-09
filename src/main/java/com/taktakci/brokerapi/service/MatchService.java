package com.taktakci.brokerapi.service;

import com.taktakci.brokerapi.controller.dto.MatchDto;
import com.taktakci.brokerapi.exception.BrokerException;
import com.taktakci.brokerapi.repository.OrderRepository;
import com.taktakci.brokerapi.repository.entity.Order;
import com.taktakci.brokerapi.service.model.OrderSide;
import com.taktakci.brokerapi.service.model.OrderStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.taktakci.brokerapi.service.AssetService.ASSET_TRY;

@Service
@Slf4j
public class MatchService {

    private final OrderRepository orderRepository;
    private final AssetService assetService;

    public MatchService(OrderRepository orderRepository, AssetService assetService) {
        this.orderRepository = orderRepository;
        this.assetService = assetService;
    }

    public void matchOrder(MatchDto matchDto) {
        Order buyOrder = getOrder(matchDto.getBuyOrderId());
        Order sellOrder = getOrder(matchDto.getSellOrderId());

        validateTransaction(buyOrder, sellOrder);

        doTransaction(buyOrder, sellOrder);
    }

    private void doTransaction(Order buyOrder, Order sellOrder) {

        Integer buyer = buyOrder.getCustomerId();
        Integer seller = sellOrder.getCustomerId();
        var buyerPrice = buyOrder.getPrice() * buyOrder.getSize();
        var sellerPrice = sellOrder.getPrice() * sellOrder.getSize();

        assetService.decreaseAssetSize(buyer, ASSET_TRY, sellerPrice);
        assetService.increaseUsableSize(buyer, ASSET_TRY, buyerPrice - sellerPrice);
        assetService.increaseAssetSize(seller, ASSET_TRY, sellerPrice);
        assetService.increaseUsableSize(seller, ASSET_TRY, sellerPrice);

        buyOrder.setStatus(OrderStatus.MATCHED);
        orderRepository.save(buyOrder);

        var assetName = buyOrder.getAssetName();
        var assetSize = buyOrder.getSize();
        assetService.increaseAssetSize(buyer, assetName, assetSize);
        assetService.increaseUsableSize(buyer, assetName, assetSize);
        assetService.decreaseAssetSize(seller, assetName, assetSize);

        sellOrder.setStatus(OrderStatus.MATCHED);
        orderRepository.save(sellOrder);
    }

    private static void validateTransaction(Order buyOrder, Order sellOrder) {

        if (buyOrder.getCustomerId().equals(sellOrder.getCustomerId())) {
            throw new BrokerException("Buyer and Seller cannot be the same person!");
        }

        if (!buyOrder.getStatus().equals(OrderStatus.PENDING)) {
            throw new BrokerException("buyOrder status must be PENDING!");
        }
        if (!sellOrder.getStatus().equals(OrderStatus.PENDING)) {
            throw new BrokerException("sellOrder status must be PENDING!");
        }

        if (!buyOrder.getOrderSide().equals(OrderSide.BUY)) {
            throw new BrokerException("buyOrder side is not BUY!");
        }
        if (!sellOrder.getOrderSide().equals(OrderSide.SELL)) {
            throw new BrokerException("sellOrder side is not SELL!");
        }


        if (!buyOrder.getAssetName().equals(sellOrder.getAssetName())) {
            throw new BrokerException("Buyer and Seller assets are different!");
        }

        if (!buyOrder.getSize().equals(sellOrder.getSize())) {
            throw new BrokerException("Buyer and Seller asset sizes are different!");
        }

        if (buyOrder.getPrice() < sellOrder.getPrice()) {
            throw new BrokerException("Buyer price cannot be less than seller price!");
        }
    }

    private Order getOrder(Integer orderId) {
        Optional<Order> orderOptional = orderRepository.findById(orderId);

        if (orderOptional.isEmpty()) {
            throw new BrokerException("order not found!");
        }

        return orderOptional.get();
    }

}
