package com.sysbeckysfloristeria.g3.main.service;

import com.sysbeckysfloristeria.g3.main.model.User;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserDTO;
import com.sysbeckysfloristeria.g3.main.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService, UserDetailsService {

    //Dependency injection IUserRepository
    @Autowired
    private IUserRepository userRepository;

    //Dependency injection PasswordEncoder
    @Autowired
    private PasswordEncoder passwordEncoder;

    //converts the user entity to userDTO
    private UserDTO convertToDTO(User user){
        return new UserDTO(user.getName(),user.getLastName(),user.getEmail(),user.getPhone());
    }

    @Override
    public List<UserDTO> getAllUser() {
        List<User> users= userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public User saveUser(User user) {
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        return userRepository.save(user);
    }

    @Override
    public User editUser(User user) {
        if(user.getPassword()!=null && !user.getPassword().isEmpty()){
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        return this.saveUser(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public List<UserDTO> dinByWord(String word) {
        List<User> users= userRepository.findByNameContaining(word);
        return users.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public String deletById(Long id) {
        userRepository.deleteById(id);
        return "Usuario eliminado con exito";
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findByName(username)
                .orElseThrow(()->new UsernameNotFoundException("usr not found " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRol().name()))
        );
    }
}
