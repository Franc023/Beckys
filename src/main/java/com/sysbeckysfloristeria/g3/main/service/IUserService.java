package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.User;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDTO> getAllUser();
    User saveUser(User user);
    User editUser(User user);
    Optional<User> findById(Long id);
    List<UserDTO> dinByWord(String word);
    String deletById(Long id);
}
