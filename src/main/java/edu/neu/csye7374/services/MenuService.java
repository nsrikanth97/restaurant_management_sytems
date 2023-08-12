package edu.neu.csye7374.services;

import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.Dish;
import edu.neu.csye7374.repository.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.orm.jpa.JpaSystemException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class MenuService {

    @Autowired
    private DishRepository dishRepository;

    private static MenuService instance = null;
    private List<Dish> dishes;
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

    public ResponseEntity<Dish> addDish(Dish d) {
        ResponseEntity<Dish> responseEntity = null;
        try{
            Dish dish =dishRepository.save(d);
            dishes.add(dish);
            responseEntity = new ResponseEntity<>("Dish added successfully",dish, ReturnType.SUCCESS);
        }catch (DataIntegrityViolationException e){
            responseEntity = new ResponseEntity<>("Dish already exists",null, ReturnType.FAILURE);
            responseEntity.setStackTraceElements(e.getStackTrace());
        }catch( JpaSystemException e){
            responseEntity = new ResponseEntity<>("System Error,Failed to save data to the database, please try again after some time",null, ReturnType.FAILURE);
            responseEntity.setStackTraceElements(e.getStackTrace());
        }catch (Exception e){
            responseEntity = new ResponseEntity<>("System Error,please try again after some time",null, ReturnType.FAILURE);
            responseEntity.setStackTraceElements(e.getStackTrace());
        }
        return responseEntity;
    }

    private synchronized void loadDishes(){
        try{
            if(dishes == null){
                dishes = dishRepository.findAll();
            }
        }catch(Exception ex){
            dishes = null;
            System.out.println("Failed in fetching the dishes from database");
        }

    }

    public List<Dish> getDishes() {
        loadDishes();
        return dishes;
    }

    public ResponseEntity<Dish> updateDish(Dish dish) {
        dishes.removeIf(d -> Objects.equals(d.getId(), dish.getId()));
    	return addDish(dish);
    }

}
