package edu.neu.csye7374.entity;


import edu.neu.csye7374.dto.ReturnType;
import jakarta.persistence.*;

@lombok.Data
@Entity
@Table(name = "dish")
public abstract class Dish {

    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "dish_id_generator")
    @Id
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "price", nullable = false)
    private double price;

    @Column(name = "is_vegan")
    private boolean isVegan;

    @Column(name = "is_gluten_free")
    private boolean isGlutenFree;

    @Column(name = "preparation_time")
    private int preparationTime;

    @Column(name = "calories")
    private int calories;

    @Column(name = "dish_type")
    @Enumerated(EnumType.STRING)
    private ReturnType.DishType dishType;

    public Dish(String name, String description, double price, boolean isVegan, boolean isGlutenFree, int preparationTime, int calories, ReturnType.DishType dishType) {
        this.name = name;
        this.description = description;
        this.price = price;
        this.isVegan = isVegan;
        this.isGlutenFree = isGlutenFree;
        this.preparationTime = preparationTime;
        this.calories = calories;
        this.dishType = dishType;
    }
}
