package com.example.marketplace.model;

public class UserSingleton {

    private static UserSingleton instance;
    private User currentUser;

    private UserSingleton() {
        // Private constructor to prevent instantiation
    }

    public static synchronized UserSingleton getInstance() {
        if (instance == null) {
            instance = new UserSingleton();
        }
        return instance;
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User user) {
        this.currentUser = user;
    }

    public boolean isLoggedIn() {
        return currentUser != null;
    }

    public void logout() {
        currentUser = null;
    }
}

