package com.taktakci.brokerapi.controller;

import com.taktakci.brokerapi.controller.dto.OrderDto;
import com.taktakci.brokerapi.repository.entity.Order;
import com.taktakci.brokerapi.service.OrderService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/order")
@Slf4j
public class OrderController {

    private final ModelMapper mapper;
    private final OrderService service;

    public OrderController(ModelMapper mapper, OrderService service) {
        this.mapper = mapper;
        this.service = service;
    }

    @GetMapping("/customer/{customerId}/fromDate/{fromDate}/toDate/{toDate}")
    public ResponseEntity<List<OrderDto>> listOrders(@PathVariable("customerId") Integer customerId,
                                                     @PathVariable("fromDate") @DateTimeFormat(pattern = "yy-MM-dd") LocalDate fromDate,
                                                     @PathVariable("toDate") @DateTimeFormat(pattern = "yy-MM-dd") LocalDate toDate) {
        log.info("listOrders request received for customer id - {}", customerId);

        List<Order> orderList = service.listOrders(customerId, fromDate, toDate);
        List<OrderDto> orderDtoList = mapper.map(orderList, List.class);

        log.info("listOrders request returning for customer id - {}", customerId);
        return new ResponseEntity<>(orderDtoList, HttpStatus.OK);
    }

    @PostMapping("")
    public ResponseEntity<OrderDto> createOrder(@RequestBody @Valid OrderDto orderDto) {
        log.info("createOrder request received - {}", orderDto);

        Order order = mapper.map(orderDto, Order.class);
        order = service.createOrder(order);
        orderDto = mapper.map(order, OrderDto.class);

        log.info("createOrder request is returning - {}", orderDto);
        return new ResponseEntity<>(orderDto, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Integer> deleteOrder(@PathVariable("id") Integer id) {
        log.info("deleteOrder request received for id - {}", id);

        service.deleteOrder(id);

        log.info("deleteOrder request returning for id - {}", id);
        return new ResponseEntity<>(id, HttpStatus.OK);
    }
}
