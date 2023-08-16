package edu.neu.csye7374.repository;

import edu.neu.csye7374.decorators.BaseDishDecorator;
import edu.neu.csye7374.entity.User;

public interface UserRepository extends BaseRepository<User> {

    User findByUsernameOrEmail(String username, String email);
    User findByUsername(String username);

    User findByEmail(String email);

    User findByPhone(String phone);
}
