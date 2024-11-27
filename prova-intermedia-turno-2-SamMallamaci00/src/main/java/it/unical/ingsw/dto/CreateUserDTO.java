package it.unical.ingsw.dto;

import it.unical.ingsw.entities.User;

public class CreateUserDTO {
    private String name;
    private String password;
    private String email;


    public CreateUserDTO(String name, String password, String email) {
        this.name = name;
        this.password = password;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String passwordMd5) {
        this.password = passwordMd5;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public User toUser() {
        return new User(this.name, this.password, this.email);
    }

}
