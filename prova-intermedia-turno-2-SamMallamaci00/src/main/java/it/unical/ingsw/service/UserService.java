package it.unical.ingsw.service;

import it.unical.ingsw.dto.CreateUserDTO;
import it.unical.ingsw.dto.UserDTO;

public interface UserService {

    UserDTO findUserByEmail(String email);

    UserDTO createUser(CreateUserDTO dto) throws Exception;
}
