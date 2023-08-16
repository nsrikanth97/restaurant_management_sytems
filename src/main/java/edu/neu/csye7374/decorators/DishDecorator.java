package edu.neu.csye7374.decorators;

import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.strategies.PricingStrategy;

import java.util.UUID;

public class DishDecorator implements BaseDishAPI {

    private final BaseDishAPI baseDishDecorator;

    public DishDecorator(BaseDishAPI baseDishDecorator) {
        this.baseDishDecorator = baseDishDecorator;
    }

    @Override
    public UUID getId() {
        return baseDishDecorator.getId();
    }

    @Override
    public String getName() {
        return baseDishDecorator.getName();
    }
    @Override
    public String getDescription() {
        return baseDishDecorator.getDescription();
    }

    @Override
    public double getPrice() {
        return baseDishDecorator.getPrice();
    }

    @Override
    public boolean isVegan() {
        return baseDishDecorator.isVegan();
    }

    @Override
    public boolean isGlutenFree() {
        return baseDishDecorator.isGlutenFree();
    }

    @Override
    public int getPreparationTime() {
        return baseDishDecorator.getPreparationTime();
    }

    @Override
    public int getCalories() {
        return baseDishDecorator.getCalories();
    }

    @Override
    public ReturnType.DishType getDishType() {
        return baseDishDecorator.getDishType();
    }

    @Override
    public void setPricingStrategy(PricingStrategy pricingStrategy) {
        baseDishDecorator.setPricingStrategy(pricingStrategy);
    }


}
