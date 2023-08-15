package edu.neu.csye7374.entity;


import com.fasterxml.jackson.annotation.JsonIgnore;
import edu.neu.csye7374.decorators.BaseDishDecorator;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.strategies.PricingStrategy;
import jakarta.persistence.*;

import java.util.UUID;

@lombok.Data
@Entity
@Table(name = "dish")
public class Dish implements BaseDishDecorator {

    @Transient
    @JsonIgnore
    private PricingStrategy pricingStrategy;


    @Id
    @Column(unique = true, name = "id", nullable = false)
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

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

    public double getPrice() {
        if(pricingStrategy == null){
            return price;
        }
        return pricingStrategy.calculatePrice(price);
    }




    private Dish() {
    }

    public static class DishBuilder {
        private final String name;

        private String description;
        private final double price;
        private boolean isVegan;
        private boolean isGlutenFree;
        private int preparationTime;
        private int calories;
        private ReturnType.DishType dishType;

        private PricingStrategy pricingStrategy;

        public DishBuilder(String name, double price) {
            this.name = name;
            this.price = price;
        }

        public DishBuilder setDescription(String description) {
            this.description = description;
            return this;
        }

        public DishBuilder isVegan(boolean isVegan) {
            this.isVegan = isVegan;
            return this;
        }

        public DishBuilder isGlutenFree(boolean isGlutenFree) {
            this.isGlutenFree = isGlutenFree;
            return this;
        }

        public DishBuilder setPreparationTime(int preparationTime) {
            this.preparationTime = preparationTime;
            return this;
        }

        public DishBuilder setCalories(int calories) {
            this.calories = calories;
            return this;
        }

        public DishBuilder setDishType(ReturnType.DishType dishType) {
            this.dishType = dishType;
            return this;
        }

        public DishBuilder setPricingStrategy(PricingStrategy pricingStrategy) {
            this.pricingStrategy = pricingStrategy;
            return this;
        }
        public Dish build() {
            Dish dish = new Dish();
            dish.name = this.name;
            dish.description = this.description;
            dish.price = this.price;
            dish.isVegan = this.isVegan;
            dish.isGlutenFree = this.isGlutenFree;
            dish.preparationTime = this.preparationTime;
            dish.calories = this.calories;
            dish.dishType = this.dishType;
            dish.pricingStrategy = this.pricingStrategy;
            return dish;
        }
    }

}
