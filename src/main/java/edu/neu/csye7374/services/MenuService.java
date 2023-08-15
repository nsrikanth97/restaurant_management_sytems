package edu.neu.csye7374.services;

import edu.neu.csye7374.decorators.BaseDishDecorator;
import edu.neu.csye7374.dto.DishInput;
import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.ComboDish;
import edu.neu.csye7374.entity.Dish;
import edu.neu.csye7374.repository.ComboDishRepository;
import edu.neu.csye7374.repository.DishRepository;
import edu.neu.csye7374.strategies.PricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class MenuService {

    @Autowired
    private DishRepository dishRepository;

    @Autowired
    private ComboDishRepository comboDishRepository;

    private static MenuService instance = null;
    private List<BaseDishDecorator> dishes;
    private MenuService() {
    }
    public static MenuService getInstance() {
        synchronized(MenuService.class) {
            if (instance == null) {
                instance = new MenuService();
            }
        }
        return instance;
    }

    public BaseDishDecorator getDish(UUID id){
        loadDishes();
        for(BaseDishDecorator dish: dishes){
            if(Objects.equals(dish.getId(), id)){
                return dish;
            }
        }
        return null;
    }

    public ResponseEntity<Dish> addDish(DishInput d, boolean isCustomized) {
        if(d.getDishType() == ReturnType.DishType.COMBO){
            ComboDish comboDish = new ComboDish(d.getName());
            for(UUID dishId: d.getDishes()){
                BaseDishDecorator dish = getDish(dishId);
                if(dish == null || dish.getDishType() == ReturnType.DishType.COMBO){
                    return new ResponseEntity<>("Invalid dish id", null, ReturnType.FAILURE);
                }
                comboDish.addDish((Dish) dish);
            }
//            return addDish(comboDish, isCustomized);
        }
        Dish dish =
                new Dish.DishBuilder(d.getName(),d.getPrice())
                        .setDescription(d.getDescription())
                        .setDishType(d.getDishType())
                        .setCalories(d.getCalories())
                        .setPreparationTime(d.getPreparationTime())
                        .isGlutenFree(d.isGlutenFree())
                        .isVegan(d.isVegan()).build();
        return addDish(dish,false);
    }

    public ResponseEntity<Dish> addDish(Dish dish, boolean isCustomized){

        ResponseEntity<Dish> response =dishRepository.saveAndCatch(dish);
        if(response.getResponseStatus() == ReturnType.FAILURE){
            return response;
        }else{
            dish = response.getData();
        }
        if(dishes == null){
            dishes = new ArrayList<>();
        }
        if(!isCustomized){
            dishes.add(dish);
        }
        response = new ResponseEntity<>("Dish added successfully",dish, ReturnType.SUCCESS);
        return response;
    }

    private synchronized void loadDishes(){
        try{
            if(dishes == null){
                dishes = new ArrayList<>();
                dishes.addAll(dishRepository.findAll());
                dishes.addAll(comboDishRepository.findAll());
            }
        }catch(Exception ex){
            dishes = null;
            System.out.println("Failed in fetching the dishes from database");
        }
    }



    public List<BaseDishDecorator> getDishes() {
        loadDishes();
        return dishes;
    }

    public void updatePricingStrategy(PricingStrategy pricingStrategy) {
        loadDishes();
        for(BaseDishDecorator dish: dishes){
            dish.setPricingStrategy(pricingStrategy);
        }
    }

    public ResponseEntity<BaseDishDecorator> updateDish(BaseDishDecorator dish) {
        UUID id = dish.getId();
        dishes.removeIf(d -> Objects.equals(d.getId(), id));
        ResponseEntity<ComboDish> comboDishResponse;
        ResponseEntity<Dish> dishResponse;
        if(dish.getDishType() == ReturnType.DishType.COMBO){
            comboDishResponse  =comboDishRepository.saveAndCatch((ComboDish) dish);
            if(comboDishResponse.getResponseStatus() == ReturnType.FAILURE){
                return new ResponseEntity<>(comboDishResponse.getMessage(),dish, ReturnType.FAILURE);
            }
            dish = comboDishResponse.getData();
        }else{
            dishResponse = dishRepository.saveAndCatch((Dish) dish);
            if(dishResponse.getResponseStatus() == ReturnType.FAILURE){
                return new ResponseEntity<>(dishResponse.getMessage(),dish, ReturnType.FAILURE);
            }
            dish = dishResponse.getData();
        }

        if(dishes == null){
            dishes = new ArrayList<>();
        }
        dishes.add(dish);
        return new ResponseEntity<>("Dish updated successfully",dish, ReturnType.SUCCESS);
    }



}
