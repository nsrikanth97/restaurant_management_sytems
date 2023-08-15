package edu.neu.csye7374.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye7374.dto.DishInput;
import edu.neu.csye7374.dto.Status;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "orders")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(unique = true, name = "id", nullable = false)
    private UUID id;

    @Column(name = "date_time")
    private Date dateTime;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "status")
    private Status status;

    @Column(name = "total_price")
    private double totalPrice;

    @Column(name = "payment_method")
    private String paymentMethod;

    @Column(name = "payment_status")
    private Status paymentStatus;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    List<OrderDish> orderDishes;

    @Transient
    private List<DishInput> dishes;

    public Order() {
    }

}
