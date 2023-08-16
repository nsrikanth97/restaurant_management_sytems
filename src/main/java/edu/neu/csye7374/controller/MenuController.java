package edu.neu.csye7374.controller;

import edu.neu.csye7374.config.JwtService;
import edu.neu.csye7374.decorators.BaseDishAPI;
import edu.neu.csye7374.dto.DishInput;
import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.services.MenuService;
import edu.neu.csye7374.services.UserService;
import edu.neu.csye7374.strategies.HappyHourPricingStrategy;
import edu.neu.csye7374.strategies.PeakHourPricingStrategy;
import edu.neu.csye7374.strategies.RegularPricingStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserService userService;

    @RequestMapping( method =RequestMethod.GET)
    public ResponseEntity<List<BaseDishAPI>> getMenu(){
        ResponseEntity<List<BaseDishAPI>> response = new ResponseEntity<>();
        List<BaseDishAPI> dishes = menuService.getDishes();
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
    public ResponseEntity<BaseDishAPI> addDish(@RequestBody DishInput dishInput){
        return menuService.addDish(dishInput,false);
    }

    @RequestMapping(value = "pricingStrategy", method = RequestMethod.GET)
    public ResponseEntity<List<BaseDishAPI>> getUpdatedMenu(@RequestParam String strategy){
        List<BaseDishAPI> dishes;
        if(strategy == null || strategy.isEmpty()){
            return new ResponseEntity<>("Invalid strategy", null, ReturnType.FAILURE);
        }else if(strategy.equalsIgnoreCase("happyhour")){
            menuService.updatePricingStrategy(new HappyHourPricingStrategy());
        }else if(strategy.equalsIgnoreCase("regular")){
            menuService.updatePricingStrategy(new RegularPricingStrategy());
        }else if(strategy.equalsIgnoreCase("peakhour")){
            menuService.updatePricingStrategy(new PeakHourPricingStrategy());
        }else{
            return new ResponseEntity<>("Invalid strategy, valid strategies are happyhour, regular and peakhour", null, ReturnType.FAILURE);
        }
        dishes = menuService.getDishes();
        return new ResponseEntity<>("Prices updated successfully", dishes, ReturnType.SUCCESS);
    }
}
