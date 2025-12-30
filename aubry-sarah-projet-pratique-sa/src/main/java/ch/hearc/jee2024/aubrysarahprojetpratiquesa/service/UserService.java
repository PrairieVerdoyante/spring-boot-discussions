package ch.hearc.jee2024.aubrysarahprojetpratiquesa.service;

import ch.hearc.jee2024.aubrysarahprojetpratiquesa.UserEntity;

import java.util.Optional;

public interface UserService {
    UserEntity createUser(String username, String password, String role);
    Optional<UserEntity> findByUsername(String username);
}
