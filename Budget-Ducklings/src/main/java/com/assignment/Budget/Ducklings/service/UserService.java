package com.assignment.Budget.Ducklings.service;

import com.assignment.Budget.Ducklings.model.AuthUser;
import com.assignment.Budget.Ducklings.repository.UserRepository;

public class UserService {

    private UserRepository userRepository;

    public UserService() {
        this.userRepository = new UserRepository();
    }

    public AuthUser getUser(String username, String password) {
        return userRepository.getUser(username, password);
    }

}
