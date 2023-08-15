package edu.neu.csye7374.strategies;

public class PeakHourPricingStrategy implements PricingStrategy{

    @Override
    public double calculatePrice(double price) {
        return price * 1.2;
    }
}
