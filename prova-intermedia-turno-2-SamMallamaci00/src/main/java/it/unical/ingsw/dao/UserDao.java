package it.unical.ingsw.dao;

import it.unical.ingsw.entities.User;

import java.util.List;

public interface UserDao {
    User updateUser(User user);

    void deleteUser(User user);

    List<User> getAllUsers();

    User getUserByEmail(String email);

    User save(User user);
}
