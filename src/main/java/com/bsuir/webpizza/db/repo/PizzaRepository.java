package com.bsuir.webpizza.db.repo;

import com.bsuir.webpizza.models.Pizza;
import org.springframework.data.repository.CrudRepository;

public interface PizzaRepository extends CrudRepository<Pizza, Long> {
}
