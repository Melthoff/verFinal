package com.example.springsecurityapplication.services;

import com.example.springsecurityapplication.dto.OrderSearchOptions;
import com.example.springsecurityapplication.enumm.Status;
import com.example.springsecurityapplication.models.Order;
import com.example.springsecurityapplication.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrdersService {

    @Autowired
    OrderRepository orderRepository;

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public void setStatus(int id, Status status) {
        Optional<Order> byId = orderRepository.findById(id);
        if (byId.isPresent()) {
            Order order = byId.get();
            order.setStatus(status);
            orderRepository.save(order);
        }
    }

    public List<Order> findAll(OrderSearchOptions searchOptions) {
        return orderRepository.findAll().stream().filter(o -> filterBySearchOptions(o, searchOptions)).collect(Collectors.toList());
    }

    private boolean filterBySearchOptions(Order order, OrderSearchOptions searchOptions) {
        if (searchOptions.getLastFourLetters() != null && !searchOptions.getLastFourLetters().isEmpty()) {
            if (order.getNumber().length() >= 4) {
                return order.getNumber().substring(order.getNumber().length() - 4).contains(searchOptions.getLastFourLetters());
            } else {
                return false;
            }
        }
        return true;
    }
}
