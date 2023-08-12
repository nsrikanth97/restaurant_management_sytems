package edu.neu.csye7374.entity;

import jakarta.persistence.*;

@Entity
public class OrderDish {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "order_dish_id_generator")
    @Column(unique = true, name = "id", nullable = false)
    private Long id;

    @JoinColumn(name = "dish_id")
    @ManyToOne
    private Dish dish;

    @Column(name = "quantity")
    private int quantity;

    @JoinColumn(name = "order_id")
    @ManyToOne
    private Order order;
}
