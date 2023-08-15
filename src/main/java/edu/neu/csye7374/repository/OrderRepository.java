package edu.neu.csye7374.repository;

import edu.neu.csye7374.dto.Status;
import edu.neu.csye7374.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {

    List<Order> findByStatus(Status status);
}
