package edu.neu.csye7374.dto;

public enum ReturnType {
    SUCCESS,
    FAILURE;

    public enum DishType {
        APPETIZER, MAIN_COURSE, DESSERT, BEVERAGE;

        public String toString() {
            return switch (this) {
                case APPETIZER -> "Appetizer";
                case MAIN_COURSE -> "Main Course";
                case DESSERT -> "Dessert";
                case BEVERAGE -> "Beverage";
                default -> throw new IllegalArgumentException();
            };
        }
    }
}
