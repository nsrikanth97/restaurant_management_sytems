package edu.neu.csye7374.interfaces;

import edu.neu.csye7374.entity.Order;

public interface OrderObserver {

    void update(Order order);
}
