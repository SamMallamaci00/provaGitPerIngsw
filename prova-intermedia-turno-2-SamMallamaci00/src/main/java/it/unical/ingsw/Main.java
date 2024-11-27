package it.unical.ingsw;

import it.unical.ingsw.dao.UserDao;
import it.unical.ingsw.dto.CreateUserDTO;
import it.unical.ingsw.dto.UserConverter;
import it.unical.ingsw.dto.UserDTO;
import it.unical.ingsw.entities.User;
import it.unical.ingsw.service.EmailService;
import it.unical.ingsw.service.SecurityService;
import it.unical.ingsw.service.UserService;
import it.unical.ingsw.service.UserServiceImpl;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.List;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {

        UserDao userDao = new UserDao() {
            @Override
            public User updateUser(User user) {
                System.out.println("Fake user dao");
                return user;
            }

            @Override
            public void deleteUser(User user) {
                System.out.println("User deleted");
            }

            @Override
            public List<User> getAllUsers() {
                return null;
            }

            @Override
            public User getUserByEmail(String email) {

                return null;
            }

            @Override
            public User save(User user) {
                user.setId(UUID.randomUUID().toString());
                return user;
            }
        };
        SecurityService secService = password -> {
            String md5Hex = DigestUtils
                    .md5Hex(password).toUpperCase();
            System.out.println("Hashed password: " + md5Hex);
            return md5Hex;
        };

        EmailService emailService = email -> System.out.println("Email sent");

        UserService userService = new UserServiceImpl(userDao, secService, emailService, new UserConverter());
        CreateUserDTO user = new CreateUserDTO("Name", "password", "user@unical.it");
        try {
            UserDTO createdUser = userService.createUser(user);
            System.out.println("Created user id: " + createdUser.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
