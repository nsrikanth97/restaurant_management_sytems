package edu.neu.csye7374.strategies;

public class HappyHourPricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(double price) {
        return price * 0.8;
    }

}
