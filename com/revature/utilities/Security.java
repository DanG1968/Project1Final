package com.revature.utilities;

import com.revature.domain.User;

import java.util.HashMap;
import java.util.Map;

public class Security {

    public static Map<String, User> users = new HashMap<>();

    public static String getRole(String token) {
        User user = users.get(token);
        if (user != null) {
            return user.getRole();
        }
        return null;
    }

    public static User getLoggedUser(String token){
        return users.get(token);
    }

    public static void addUser(String token, User user) {
        users.put(token, user);
    }

    public static boolean isManager(String token) {
        User user = users.get(token);
        return user != null && user.getRole().equals("MANAGER");
    }

    public static boolean isEmployee(String token) {
        User user = users.get(token);
        return user != null && user.getRole().equals("EMPLOYEE");
    }

}
