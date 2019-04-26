package com.revature.dao;

import com.revature.domain.User;

public interface UserDAO {

    User getUserByUsernameAndPassword(String username, String password);

}
