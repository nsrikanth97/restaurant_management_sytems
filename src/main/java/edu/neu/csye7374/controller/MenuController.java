package edu.neu.csye7374.controller;

import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.Dish;
import edu.neu.csye7374.services.MenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    private final MenuService menuService = MenuService.getInstance();

    @RequestMapping( method =RequestMethod.GET)
    public ResponseEntity<List<Dish>> getMenu(){
        ResponseEntity<List<Dish>> response = new ResponseEntity<>();
        List<Dish> dishes = menuService.getDishes();
        if(dishes == null){
            response.setMessage("No dishes found, please add the dish and check back");
            response.setResponseStatus(ReturnType.SUCCESS);
        }else{
            response.setData(dishes);
            response.setResponseStatus(ReturnType.SUCCESS);
        }

        return response;
    }

    @RequestMapping(value = "/addDish", method = RequestMethod.POST)
    public ResponseEntity<Dish> addDish(@RequestBody Dish dish){
        return menuService.addDish(dish);
    }
}
