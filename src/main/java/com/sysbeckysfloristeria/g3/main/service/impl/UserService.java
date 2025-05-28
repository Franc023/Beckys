package com.sysbeckysfloristeria.g3.main.service.impl;

import com.sysbeckysfloristeria.g3.main.exception.BadRequestException;
import com.sysbeckysfloristeria.g3.main.exception.ResourceNotFoundException;
import com.sysbeckysfloristeria.g3.main.exception.UnauthorizedException;
import com.sysbeckysfloristeria.g3.main.model.ProductCart;
import com.sysbeckysfloristeria.g3.main.model.Role;
import com.sysbeckysfloristeria.g3.main.model.User;
import com.sysbeckysfloristeria.g3.main.modelDTO.*;
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
        if (user == null) {
            throw new BadRequestException("No se puede convertir un usuario nulo a DTO");
        }
        return new UserDto(user.getName(),user.getLastName(),user.getEmail(),user.getPhone());
    }


    @Override
    public List<UserDto> getAllUser() {
        List<User> users = userRepository.findAll();
        if (users.isEmpty()) {
            throw new ResourceNotFoundException("No se encontraron usuarios registrados");
        }
        return users.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public void saveUser(UserSaveDto userDto) {
        if (userDto == null) {
            throw new BadRequestException("Los datos del usuario no pueden ser nulos");
        }

        userRepository.findByEmail(userDto.getEmail())
                .ifPresent(user -> {
                    throw new BadRequestException("El email " + userDto.getEmail() + " ya está registrado");
                });

        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getNumber());
        user.setRol(userDto.getRole() != null ? userDto.getRole() : Role.User);
        user.setPassword(encodedPassword);

        userRepository.save(user);
    }

    @Override
    public void editUser(UserEditDto userDto) {
        Optional<User> optionalUser = userRepository.findById(userDto.getId());
        if (optionalUser.isEmpty()) {
            throw new ResourceNotFoundException("Usuario con ID " + userDto.getId() + " no existe.");
        }

        User user = optionalUser.get();
        user.setName(userDto.getName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPhone(userDto.getNumber());

        userRepository.save(user);
    }

    @Override
    public Optional<User> findById(Long id) {
        if (id == null) {
            throw new BadRequestException("El ID no puede ser nulo");
        }
        return Optional.ofNullable(userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado")));
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
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UnauthorizedException("Usuario no encontrado con email: " + email));

        return new org.springframework.security.core.userdetails.User(
                user.getEmail(),
                user.getPassword(),
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + user.getRol().name()))
        );
    }
}
