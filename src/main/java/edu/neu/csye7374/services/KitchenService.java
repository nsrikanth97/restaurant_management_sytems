package edu.neu.csye7374.services;

import edu.neu.csye7374.dto.Status;
import edu.neu.csye7374.entity.Order;
import edu.neu.csye7374.interfaces.OrderObserver;
import edu.neu.csye7374.logger.Logger;
import edu.neu.csye7374.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;


public class KitchenService {

    @Autowired
    private OrderRepository orderRepository ;

    @Autowired
    private List<OrderObserver> observers;


    private List<Order> pendingOrders;

    private KitchenService() {
    }


    private static KitchenService instance = null;

    private static Logger logger;

    public static KitchenService getInstance() {
        synchronized(KitchenService.class) {
            if (instance == null) {
                instance = new KitchenService();
                logger = Logger.getInstance();
            }
        }
        return instance;
    }

    public synchronized void loadPendingOrders(){
        if(pendingOrders == null){
            pendingOrders = orderRepository.findByStatus(Status.PREPARING);

        }
    }

    public List<Order> getPendingOrders() {
        loadPendingOrders();
        return pendingOrders;
    }

    public List<Order> updateStatus(Order order){
        this.pendingOrders.removeIf(o-> Objects.equals(o.getId(), order.getId()));
        order.setStatus(Status.READY);
        notifyOrderService(order);
        return pendingOrders;
    }


    private void notifyOrderService(Order order){
        this.observers.forEach(observer -> observer.update(order));
    }


}
