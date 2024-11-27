package it.unical.ingsw.service;

import it.unical.ingsw.dao.UserDao;
import it.unical.ingsw.dto.CreateUserDTO;
import it.unical.ingsw.dto.UserConverter;
import it.unical.ingsw.dto.UserDTO;
import it.unical.ingsw.entities.User;
import it.unical.ingsw.exceptions.UserAlreadyExistsException;

public class UserServiceImpl implements UserService {

    private UserDao userDao;
    private SecurityService securityService;
    private EmailService emailService;
    private UserConverter converter;
    private boolean shouldVerifyEmail;

    public UserServiceImpl(UserDao userDao, SecurityService securityService, EmailService emailService, UserConverter converter) {
        this.userDao = userDao;
        this.securityService = securityService;
        this.converter = converter;
        this.emailService = emailService;
    }

    @Override
    public UserDTO createUser(CreateUserDTO dto) throws Exception {

        User existing = this.userDao.getUserByEmail(dto.getEmail());

        if (existing != null) {
            throw new UserAlreadyExistsException();
        }

        User user = this.converter.createUserDTOtoUser(dto);
        String passwordMd5 = securityService.hash(user.getPassword());
        user.setPassword(passwordMd5);

        user = this.userDao.save(user);

        if (!this.shouldVerifyEmail) {
            System.out.println("Entra nell'if");
            this.emailService.sendEmailVerificationEmail(user.getEmail());
        }

        return this.converter.userToUserDTO(user);
    }

    @Override
    public UserDTO findUserByEmail(String email) {
        return this.converter.userToUserDTO(this.userDao.getUserByEmail(email));
    }

    public boolean isShouldVerifyEmail() {
        return shouldVerifyEmail;
    }

    public void setShouldVerifyEmail(boolean shouldVerifyEmail) {
        this.shouldVerifyEmail = shouldVerifyEmail;
    }

}