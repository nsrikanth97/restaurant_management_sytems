package edu.neu.csye7374.state;


import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.dto.ReturnType;
import edu.neu.csye7374.entity.Tables;
import edu.neu.csye7374.repository.TableRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AvailableState implements TableState {

    @Autowired
    private TableRepository repository;

    @Override
    public String getName() {
        return "Available";
    }

    @Override
    public ResponseEntity<Tables> reserveTable(Tables table) {
        table.setState(new ReservedState());
        table.setCurrentState("Reserved");
        repository.save(table);
        return new ResponseEntity<>( "Table Reserved", table, ReturnType.SUCCESS);
    }

    @Override
    public ResponseEntity<Tables> occupyTable(Tables table) {
        table.setState(new ReservedState());
        table.setCurrentState("Occupied");
        repository.save(table);
        return new ResponseEntity<>( "Table Occupied", table, ReturnType.SUCCESS);
    }

    @Override
    public ResponseEntity<Tables> freeTable(Tables table) {
        return new ResponseEntity<>( "Table already available to occupy", table, ReturnType.FAILURE);
    }
}
