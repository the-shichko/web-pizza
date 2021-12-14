package com.bsuir.webpizza.controllers;

import com.bsuir.webpizza.db.repo.PizzaRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MainController {

    private final PizzaRepository _pizzaRepository;

    public MainController(PizzaRepository pizzaRepository) {
        _pizzaRepository = pizzaRepository;
    }

    @GetMapping("/")
    public String home(Model model) {
        var pizzas = _pizzaRepository.findAll();
        model.addAttribute("title", "Пиццерия");
        model.addAttribute("pizzas", pizzas);
        return "home";
    }

}