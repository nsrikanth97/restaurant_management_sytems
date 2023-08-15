package edu.neu.csye7374.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye7374.decorators.BaseDishDecorator;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.strategies.PricingStrategy;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;


@lombok.Data
@Entity(name = "combo_dish")
public class ComboDish implements BaseDishDecorator {

    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToMany
    @JoinTable(
            name = "combo_dish_dish",
            joinColumns = @JoinColumn(name = "combo_dish_id"),
            inverseJoinColumns = @JoinColumn(name = "dish_id")
    )
    @JsonIgnore
    private List<Dish> dishes;

    @Transient
    @JsonIgnore
    private PricingStrategy pricingStrategy;



    @Column(name = "name", nullable = false)
    private final String name;

    public ComboDish(String name) {
        this.dishes = new ArrayList<>();
        this.name = name;
    }



    public void addDish(Dish dish) {
        this.dishes.add(dish);
    }


    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String getDescription() {
        StringBuilder sb = new StringBuilder("Combo meal with the following dishes: ");
        for (BaseDishDecorator dish : dishes) {
            sb.append(dish.getName());
            sb.append(" ");
        }
        return sb.toString();
    }

    @Override
    public double getPrice() {
        double price = 0;
        for (BaseDishDecorator dish : dishes) {
            price += dish.getPrice();
        }
        price = price * 0.8;
        if(pricingStrategy != null) {
            price = pricingStrategy.calculatePrice(price);
        }
        return price;
    }

    @Override
    public boolean isVegan() {
        for (BaseDishDecorator dish : dishes) {
            if (dish.isVegan()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isGlutenFree() {
        for (BaseDishDecorator dish : dishes) {
            if (dish.isGlutenFree()) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int getPreparationTime() {
        int time = 0;
        for (BaseDishDecorator dish : dishes) {
            time = Math.max(dish.getPreparationTime(), time);
        }
        return time;
    }

    @Override
    public int getCalories() {
        int calories = 0;
        for (BaseDishDecorator dish : dishes) {
            calories += dish.getCalories();
        }
        return calories;
    }

    @Override
    public ReturnType.DishType getDishType() {
        return ReturnType.DishType.COMBO;
    }
}
