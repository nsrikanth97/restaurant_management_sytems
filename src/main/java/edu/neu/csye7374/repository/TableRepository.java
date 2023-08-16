package edu.neu.csye7374.repository;

import edu.neu.csye7374.entity.Tables;

public interface TableRepository extends BaseRepository<Tables>{

    Tables findByTableNumber(int tableNumber);

}
