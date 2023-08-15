package edu.neu.csye7374.repository;

import edu.neu.csye7374.entity.OrderDish;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderDishRepository extends JpaRepository<OrderDish,Long> {
}
