package edu.neu.csye7374.controller;


import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.entity.Tables;
import edu.neu.csye7374.services.TableService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = "/table")
public class TableController {


    @Autowired
    private TableService tableService;

    @RequestMapping(value = "/create" ,method = RequestMethod.POST)
    public ResponseEntity<Tables> createTable(@RequestParam int tableNumber, @RequestParam int capacity){
        return tableService.createTable(tableNumber, capacity);
    }

    @RequestMapping(value = "/getAll", method = RequestMethod.GET)
    public ResponseEntity<List<Tables>> getAllTables(){
        return tableService.getAll();
    }

    @RequestMapping(value = "/reserve", method = RequestMethod.POST)
    public ResponseEntity<Tables> reserveTable(@RequestParam int tableNumber){
        return tableService.reserveTable(tableNumber);
    }

    @RequestMapping(value = "/occupy", method = RequestMethod.POST)
    public ResponseEntity<Tables> occupyTable(@RequestParam int tableNumber){
        return tableService.occupyTable(tableNumber);
    }

    @RequestMapping(value = "/free", method = RequestMethod.POST)
    public ResponseEntity<Tables> freeTable(@RequestParam int tableNumber){
        return tableService.freeTable(tableNumber);
    }

    @RequestMapping(value = "/get", method = RequestMethod.POST)
    public ResponseEntity<Tables> getTable(@RequestParam int tableNumber){
        return tableService.getTable(tableNumber);
    }
}
