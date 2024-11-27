package it.unical.ingsw.service;
public interface SecurityService {
    String hash(String password) throws Exception;
}