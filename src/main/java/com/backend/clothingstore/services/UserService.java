package com.backend.clothingstore.services;

import com.backend.clothingstore.model.User;



public interface UserService {
    public void save(User user);
    User register(User user);
    String login(String email, String password);

    User authenticate(String email, String password);

    User updateUser(User user);

    User findByuserEmail(String name);
}
