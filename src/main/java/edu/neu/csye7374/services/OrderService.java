package edu.neu.csye7374.services;


import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.dto.Status;
import edu.neu.csye7374.entity.Order;
import edu.neu.csye7374.interfaces.KitchenObserver;
import edu.neu.csye7374.interfaces.OrderObserver;
import edu.neu.csye7374.logger.Logger;
import edu.neu.csye7374.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service
public class OrderService implements OrderObserver {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private static Logger logger;



    public ResponseEntity<Order> newOrder(Order order){
        try{
            logger.info("\n OrderService: New order received with id :"+order.getId());
            Order savedOrder = repository.save(order);
            return new ResponseEntity<>("Order created successfully", savedOrder, ReturnType.SUCCESS);
        }catch(Exception e){
            return new ResponseEntity<>("Failed to create Order",null, ReturnType.FAILURE);
        }
    }


    @Override
    public void update(Order order){
        System.out.printf("\n OrderService: Update received from the kitchen with id :%d \n",order.getId());
        repository.save(order);
    }

    public HashMap<Status, List<Order>> getPendingOrders(){
        HashMap<Status, List<Order>> map = new HashMap<>();
        map.put(Status.PREPARING, repository.findByStatus(Status.PREPARING));
        map.put(Status.READY, repository.findByStatus(Status.READY));
        return map;
    }

}
