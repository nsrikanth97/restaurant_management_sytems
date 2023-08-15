package edu.neu.csye7374.controller;


import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.Order;
import edu.neu.csye7374.services.KitchenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RequestMapping(value = "/kitchen")
@RestController
public class KitchenController {

    @Autowired
    private KitchenService kitchenService;

    @RequestMapping
    public ResponseEntity<List<Order>> getPendingOrders(){
        List<Order> orders = kitchenService.getPendingOrders();
        if(orders == null){
            return new ResponseEntity<>("No orders found, please add the order and check back", null, ReturnType.SUCCESS);
        }
        return new ResponseEntity<>("Orders pending in Kitchen", orders, ReturnType.SUCCESS);
    }

    @RequestMapping(value = "/updateStatus")
    public ResponseEntity<List<Order>>  updateStatus(@RequestBody Order order){
        List<Order> orders= kitchenService.updateStatus(order);
        return new ResponseEntity<List<Order>>("Order status updated",orders, ReturnType.SUCCESS);
    }

}
