package com.bsuir.webpizza.controllers;

import com.bsuir.webpizza.db.repo.PizzaRepository;
import com.bsuir.webpizza.viewmodels.Backet;
import com.bsuir.webpizza.viewmodels.BacketItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Controller
public class BacketController {

    private final PizzaRepository pizzaRepository;

    public BacketController(PizzaRepository pizzaRepository) {
        this.pizzaRepository = pizzaRepository;
    }

    @GetMapping("/backet/{ids}")
    public String backet(Model model, @PathVariable String ids) {
        var listIds = Arrays.stream(ids.split(" ")).toList();
        var backet = new Backet();
        backet.Items = new ArrayList<>();
        for (String s : listIds) {
            try {
                var id = Long.parseLong(s);
                var pizza = pizzaRepository.findById(id).get();
                var backetItem = new BacketItem();
                backetItem.Id = pizza.Id;
                backetItem.Price = pizza.Price;
                backetItem.Quantity = 1;
                backetItem.Name = pizza.Name;
                backet.Items.add(backetItem);

            } catch (Exception ignored) {
            }
        }
        model.addAttribute("backet", backet);
        return "backet";
    }
}
