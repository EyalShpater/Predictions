package user.api;

import user.impl.User;

import java.util.*;

public class UserManager {
    private final Map<String, User> nameToUser;

    public UserManager() {
        nameToUser = new HashMap<>();
    }

    public User getUser(String userName) {
        return nameToUser.get(userName);
    }

    public boolean isUserExists(String userName) {
        return nameToUser.containsKey(userName);
    }

    public boolean isUserLoggedIn(String userName) {
        return nameToUser.get(userName).isConnected();
    }

    public void logInUser(String userName) {
        if (!isUserExists(userName)) {
            addUser(userName);
        }

        nameToUser.get(userName).connect();
    }

    private synchronized void addUser(String userName) {
        nameToUser.putIfAbsent(userName, new User(userName));
    }

    public void logOutUser(String userName) {
        nameToUser.get(userName).disconnect();
    }
}







