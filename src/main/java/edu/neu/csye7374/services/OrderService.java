package edu.neu.csye7374.services;

import edu.neu.csye7374.decorators.BaseDishAPI;
import edu.neu.csye7374.decorators.CheeseDecorator;
import edu.neu.csye7374.decorators.ExtraMeatDecorator;
import edu.neu.csye7374.decorators.ToppingDecorator;
import edu.neu.csye7374.dto.DishInput;
import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.dto.Status;
import edu.neu.csye7374.entity.Dish;
import edu.neu.csye7374.entity.Order;
import edu.neu.csye7374.entity.OrderDish;
import edu.neu.csye7374.interfaces.OrderObserver;
import edu.neu.csye7374.logger.Logger;
import edu.neu.csye7374.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.sql.Date;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class OrderService implements OrderObserver {

    @Autowired
    private OrderRepository repository;

    @Autowired
    private MenuService menuService;

    @Autowired
    private Logger logger;


    public ResponseEntity<Order> newOrder(Order order) {
        try {
            logger.info("\n OrderService: New order received with id :" + order.getId());
            double total = 0;
            for (DishInput d : order.getDishes()) {
                BaseDishAPI cd = menuService.getDish(d.getId());
                if(cd instanceof Dish)
                    ((Dish)cd).setPrice(d.getPrice());
                if (d.isCustomized()) {
                    if (d.getCheesePreference() > 0)
                        cd = new CheeseDecorator(cd, d.getCheesePreference());
                    if (d.getExtraMeat() != null)
                        cd = new ExtraMeatDecorator(cd, d.getExtraMeat());
                    if (!CollectionUtils.isEmpty(d.getToppings()))
                        cd = new ToppingDecorator(cd, d.getToppings());
                }
                OrderDish orderDish = new OrderDish.OrderDishBuilder(order)
                        .price(cd.getPrice())
                        .name(cd.getName())
                        .description(cd.getDescription())
                        .isVegan(cd.isVegan())
                        .isGlutenFree(cd.isGlutenFree())
                        .preparationTime(cd.getPreparationTime())
                        .calories(cd.getCalories())
                        .dishType(cd.getDishType())
                        .quantity(d.getQuantity())
                        .build();
                total += cd.getPrice() * d.getQuantity();
                if (order.getOrderDishes() == null)
                    order.setOrderDishes(new ArrayList<>());
                order.getOrderDishes().add(orderDish);
            }
            order.setTotalPrice(total);
            order.setDateTime(new Date(System.currentTimeMillis()));
            Order savedOrder = repository.save(order);
            return new ResponseEntity<>("Order created successfully", savedOrder, ReturnType.SUCCESS);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to create Order", null, ReturnType.FAILURE);
        }
    }


    @Override
    public void update(Order order) {
        logger.info("\n OrderService: Update received from the kitchen with id " + order.getId());
        repository.save(order);
    }

    public HashMap<Status, List<Order>> getPendingOrders() {
        HashMap<Status, List<Order>> map = new HashMap<>();
        map.put(Status.PREPARING, repository.findByStatus(Status.PREPARING));
        map.put(Status.READY, repository.findByStatus(Status.READY));
        map.put(Status.PENDING, repository.findByStatus(Status.PENDING));
        return map;
    }

}
