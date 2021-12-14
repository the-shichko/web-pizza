package com.bsuir.webpizza.db.repo;

import com.bsuir.webpizza.models.MyOrderPizza;
import org.springframework.data.repository.CrudRepository;

public interface MyOrderPizzaRepository extends CrudRepository<MyOrderPizza, Long> {
}
