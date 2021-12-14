package com.bsuir.webpizza.controllers;

import com.bsuir.webpizza.db.repo.MyOrderPizzaRepository;
import com.bsuir.webpizza.db.repo.MyOrderRepository;
import com.bsuir.webpizza.db.repo.PizzaRepository;
import com.bsuir.webpizza.helper.HelperService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.io.IOException;

@Controller
public class BacketController {

    private final HelperService helperService;

    public BacketController(PizzaRepository pizzaRepository, MyOrderPizzaRepository myOrderPizzaRepository, MyOrderRepository orderRepository) {
        helperService = new HelperService(pizzaRepository, myOrderPizzaRepository, orderRepository);
    }

    @GetMapping("/backet/{ids}")
    public String backet(Model model, @PathVariable String ids) throws IOException {
        model.addAttribute("backet", helperService.GetBacket(ids));
        return "backet";
    }
}
