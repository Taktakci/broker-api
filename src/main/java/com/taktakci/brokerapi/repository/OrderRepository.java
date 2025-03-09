package com.taktakci.brokerapi.repository;

import com.taktakci.brokerapi.repository.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    List<Order> findByCustomerIdAndCreateDateGreaterThanEqualAndCreateDateLessThanEqual(
            Integer customerId, LocalDate fromDate, LocalDate toDate);
}
