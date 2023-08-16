package edu.neu.csye7374.state;

import edu.neu.csye7374.dto.ResponseEntity;
import edu.neu.csye7374.entity.Tables;

public interface TableState {

    ResponseEntity<Tables> reserveTable(Tables table);

    ResponseEntity<Tables> occupyTable(Tables table);

    ResponseEntity<Tables> freeTable(Tables table);

    String getName();
}
