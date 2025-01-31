package edu.neu.csye7374.decorators;

import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.strategies.PricingStrategy;

import java.util.UUID;

public interface BaseDishAPI {
        UUID getId();
        String getName();

        String getDescription();
        double getPrice();
        boolean isVegan();
        boolean isGlutenFree();
        int getPreparationTime();
        int getCalories();
        ReturnType.DishType getDishType();


        void setPricingStrategy(PricingStrategy pricingStrategy);

}
