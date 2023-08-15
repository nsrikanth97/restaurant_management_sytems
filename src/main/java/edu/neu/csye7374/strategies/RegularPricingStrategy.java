package edu.neu.csye7374.strategies;

public class RegularPricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(double price) {
        return price;
    }
}
