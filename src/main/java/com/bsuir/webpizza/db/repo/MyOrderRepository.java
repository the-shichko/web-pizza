package com.bsuir.webpizza.db.repo;

import com.bsuir.webpizza.models.MyOrder;
import com.bsuir.webpizza.viewmodels.FullOrder;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Set;

public interface MyOrderRepository extends CrudRepository<MyOrder, Long> {
    @Query(value = "SELECT new com.bsuir.webpizza.viewmodels.FullOrder(ord.Id, ord.Name, ord.Price, ord.Status) from MyOrder ord")
    List<FullOrder> getFullList();
}
