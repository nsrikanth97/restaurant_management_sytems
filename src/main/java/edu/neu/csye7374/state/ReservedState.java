package edu.neu.csye7374.state;

import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.Tables;
import edu.neu.csye7374.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ReservedState implements TableState{

    @Autowired
    private TableRepository repository;

    @Override
    public String getName() {
        return "Reserved";
    }

    @Override
    public ResponseEntity<Tables> reserveTable(Tables table) {
        return new ResponseEntity<>( "Table already reserved", table, ReturnType.FAILURE);
    }

    @Override
    public ResponseEntity<Tables> occupyTable(Tables table) {
        table.setState(new OccupiedState());
        table.setCurrentState("Occupied");
        repository.save(table);
        return new ResponseEntity<>( "Table Occupied", table, ReturnType.SUCCESS);
    }

    @Override
    public ResponseEntity<Tables> freeTable(Tables table) {
        table.setState(new AvailableState());
        table.setCurrentState("Available");
        repository.save(table);
        return new ResponseEntity<>( "Reservation expired, table available to occupy", table, ReturnType.SUCCESS);
    }
}
