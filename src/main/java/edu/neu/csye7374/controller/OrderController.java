package edu.neu.csye7374.controller;


import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.Status;
import edu.neu.csye7374.entity.Order;
import edu.neu.csye7374.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping(value = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @RequestMapping(value = "/newOrder",method = RequestMethod.POST)
    public ResponseEntity<Order> newOrder(@RequestBody Order order){
        return orderService.newOrder(order);
    }

    @RequestMapping
    public ResponseEntity<HashMap<Status, List<Order>>> getAllOrders(){
        ResponseEntity<HashMap<Status, List<Order>>> response = new ResponseEntity<>();
        HashMap<Status, List<Order>> orders = orderService.getPendingOrders();
        if(orders == null){
            response.setMessage("No orders found, please add the order and check back");
            response.setResponseStatus(edu.neu.csye7374.dto.ReturnType.SUCCESS);
        }else{
            response.setData(orders);
            response.setResponseStatus(edu.neu.csye7374.dto.ReturnType.SUCCESS);
        }
        return response;
    }
}
