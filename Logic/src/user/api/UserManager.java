package user.api;

import user.impl.User;

import java.util.*;

public class UserManager {
    private final Map<String, User> nameToUser;

    public UserManager() {
        nameToUser = new HashMap<>();
    }

    public synchronized void addUser(String userName) {
        nameToUser.putIfAbsent(userName, new User(userName));
    }

    public User getUser(String userName) {
        return nameToUser.get(userName);
    }

    public boolean isUserExists(String username) {
        return nameToUser.containsKey(username);
    }
}







