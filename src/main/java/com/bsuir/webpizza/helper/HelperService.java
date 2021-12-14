package com.bsuir.webpizza.helper;

import com.bsuir.webpizza.db.repo.MyOrderPizzaRepository;
import com.bsuir.webpizza.db.repo.MyOrderRepository;
import com.bsuir.webpizza.db.repo.PizzaRepository;
import com.bsuir.webpizza.models.MyOrder;
import com.bsuir.webpizza.models.MyOrderPizza;
import com.bsuir.webpizza.viewmodels.Backet;
import com.bsuir.webpizza.viewmodels.BacketItem;

import java.util.*;
import java.util.stream.Collectors;z

public class HelperService {
    private final PizzaRepository pizzaRepository;
    private final MyOrderPizzaRepository myOrderPizzaRepository;
    private final MyOrderRepository orderRepository;

    public HelperService(PizzaRepository pizzaRepository, MyOrderPizzaRepository myOrderPizzaRepository, MyOrderRepository orderRepository) {
        this.pizzaRepository = pizzaRepository;
        this.myOrderPizzaRepository = myOrderPizzaRepository;
        this.orderRepository = orderRepository;
    }

    public Backet GetBacket(String ids) {
        var listIds = Arrays.stream(ids.split(" ")).toList();
        var groupIds = listIds.stream().collect(Collectors.groupingBy(s -> s));

        var backet = new Backet();
        backet.Items = new ArrayList<>();
        for (Map.Entry<String, List<String>> item : groupIds.entrySet()) {
            try {
                var id = Long.parseLong(item.getKey());
                var pizza = pizzaRepository.findById(id).get();

                var backetItem = new BacketItem();
                backetItem.Id = pizza.Id;
                backetItem.Price = pizza.Price;
                backetItem.Quantity = item.getValue().size();
                backetItem.Name = pizza.Name;
                backet.Items.add(backetItem);

            } catch (Exception ignored) {
            }
        }
        backet.Sum = backet.Items.stream().map(x -> x.Quantity * x.Price).reduce(0.0, Double::sum);
        return backet;
    }

    public boolean saveOrder(String backet) {
        var backetModel = GetBacket(backet);

        var order = new MyOrder();
        order.Price = backetModel.Sum;
        order.Name = UUID.randomUUID().toString();
        order.Status = "Готовится";
        orderRepository.save(order);

        for (BacketItem item : backetModel.Items) {
            var orderPizza = new MyOrderPizza();
            orderPizza.MyOrderId = order.Id;
            orderPizza.PizzaId = item.Id;
            orderPizza.Count = item.Quantity;
            myOrderPizzaRepository.save(orderPizza);
        }

        return true;
    }
}
