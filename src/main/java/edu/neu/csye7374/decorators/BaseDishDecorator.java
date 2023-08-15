package edu.neu.csye7374.decorators;

import edu.neu.csye7374.dto.ReturnType;
import jakarta.persistence.Entity;

public interface BaseDishDecorator {




        String getName();

        String getDescription();
        double getPrice();
        boolean isVegan();
        boolean isGlutenFree();
        int getPreparationTime();
        int getCalories();
        ReturnType.DishType getDishType();

}
