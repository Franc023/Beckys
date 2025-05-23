package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.exception.ResourceNotFoundException;
import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import com.sysbeckysfloristeria.g3.main.model.User;
import com.sysbeckysfloristeria.g3.main.modelDTO.CartDto;
import com.sysbeckysfloristeria.g3.main.modelDTO.PayDto;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserDto;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserFullInfoDto;
import com.sysbeckysfloristeria.g3.main.repository.IPayrepository;
import com.sysbeckysfloristeria.g3.main.repository.IUserRepository;
import com.sysbeckysfloristeria.g3.main.service.IUserService;
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

    @Autowired
    private IPayrepository payrepository;

    //converts the user entity to userDTO
    private UserDto convertToDTO(User user){
        return new UserDto(user.getName(),user.getLastName(),user.getEmail(),user.getPhone());
    }

    @Override
    public List<UserDto> getAllUser() {
        List<User> users= userRepository.findAll();
        return users.stream()
                .map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void saveUser(User user) {
        Optional<User> existingUser = userRepository.findByEmail(user.getEmail());
        if (existingUser.isPresent()) {
            throw new IllegalArgumentException("El email ya está registrado.");
        }

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userRepository.save(user);
    }

    @Override
    public void editUser(User user) {
        if (!userRepository.existsById(user.getId())) {
            throw new ResourceNotFoundException("Usuario con ID " + user.getId() + " no existe para editar.");
        }

        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            user.setPassword(encodedPassword);
        }
        this.saveUser(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new ResourceNotFoundException("Usuario con ID " + id + " no encontrado.");
        }
        return user;
    }

    @Override
    public List<UserDto> dinByWord(String word) {
        List<User> users = userRepository.findByNameContaining(word);
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron usuarios con el nombre que contiene: " + word);
        }
        return users.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public String deletById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ResourceNotFoundException("Usuario con ID " + id + " no existe para eliminar.");
        }
        userRepository.deleteById(id);
        return "Usuario eliminado con éxito";
    }

    public List<UserFullInfoDto> getAllUsersWithDetails() {
        List<User> users = userRepository.findAll();
        return users.stream()
                .map(user -> {
                    // Convertir List<Cart> a List<CartDto>
                    List<CartDto> cartDtos = user.getCart().stream().map(cart -> {
                        List<Long> productCartIds = cart.getProductCartList().stream()
                                .map(ProductCart::getId)
                                .collect(Collectors.toList());
                        return new CartDto(
                                cart.getId(),
                                cart.getUser() != null ? cart.getUser().getId() : null,
                                productCartIds,
                                cart.getCreationDate()
                        );
                    }).collect(Collectors.toList());

                    // Convertir List<Pay> a List<PayDto>
                    List<PayDto> payDtos = payrepository.findByUser(user).stream().map(pay -> new PayDto(
                            pay.getId(),
                            pay.getUser() != null ? pay.getUser().getId() : null,
                            pay.getCart().getId(),
                            pay.getTypePayment(),
                            pay.getFechaPago()
                    )).collect(Collectors.toList());

                    // Crear el DTO final
                    return new UserFullInfoDto(
                            user.getName(),
                            user.getEmail(),
                            cartDtos,
                            payDtos
                    );
                })
                .collect(Collectors.toList());
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByName(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado con nombre: " + username));

        return new org.springframework.security.core.userdetails.User(
                user.getName(),
                user.getPassword(),
                Collections.singleton(new SimpleGrantedAuthority("ROLE_" + user.getRol().name()))
        );
    }
}
