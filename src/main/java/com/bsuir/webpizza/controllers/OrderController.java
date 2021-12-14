package com.bsuir.webpizza.controllers;

import com.bsuir.webpizza.db.repo.MyOrderPizzaRepository;
import com.bsuir.webpizza.db.repo.MyOrderRepository;
import com.bsuir.webpizza.db.repo.PizzaRepository;
import com.bsuir.webpizza.helper.HelperService;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {
    private final HelperService helperService;
    private final MyOrderRepository orderRepository;

    public OrderController(PizzaRepository pizzaRepository, MyOrderRepository orderRepository,
                           MyOrderPizzaRepository myOrderPizzaRepository) {
        helperService = new HelperService(pizzaRepository, myOrderPizzaRepository, orderRepository);
        this.orderRepository = orderRepository;
    }

    @PostMapping("/order")
    public ResponseEntity<Boolean> saveOrder(@RequestBody String backet) {
        return new ResponseEntity<>(helperService.saveOrder(backet), HttpStatus.OK);
    }

    @GetMapping("/orders")
    public String getOrders(Model model) {
        var result = orderRepository.getFullList();
        model.addAttribute("title", "Все заказы");
        model.addAttribute("orders", result);
        return "orders";
    }
}
