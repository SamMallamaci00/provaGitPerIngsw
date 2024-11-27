package it.unical.ingsw.dto;

import it.unical.ingsw.entities.User;

public class UserConverter {


    public UserDTO userToUserDTO(User user) {
        return new UserDTO(user.getId(), user.getName(), user.getEmail(), user.getRole());
    }

    public User createUserDTOtoUser(CreateUserDTO dto) {
        return new User(dto.getName(), dto.getPassword(), dto.getEmail());
    }

}
