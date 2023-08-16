package edu.neu.csye7374.controller;


import edu.neu.csye7374.dto.AuthenticationDto;
import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.entity.User;
import edu.neu.csye7374.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/user")
public class UserController {


    @Autowired
    private UserService userService;

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    private ResponseEntity<User> createUser(@RequestBody AuthenticationDto authenticationDto){
        return userService.createUser(authenticationDto);
    }

    @RequestMapping(value = "/authenticate", method = RequestMethod.POST)
    private ResponseEntity<Boolean> authenticate(@RequestBody AuthenticationDto authenticationDto){
        return userService.authenticate(authenticationDto);
    }

}
