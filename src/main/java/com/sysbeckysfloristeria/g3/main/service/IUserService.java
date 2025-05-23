package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.User;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserDto;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserFullInfoDto;

import java.util.List;
import java.util.Optional;

public interface IUserService {
    List<UserDto> getAllUser();
    void saveUser(User user);
    void editUser(User user);
    Optional<User> findById(Long id);
    List<UserDto> dinByWord(String word);
    String deletById(Long id);
    List<UserFullInfoDto>getAllUsersWithDetails();
}
