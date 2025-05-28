package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.exception.ResourceNotFoundException;
import com.sysbeckysfloristeria.g3.main.model.User;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserDto;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserEditDto;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserSaveDto;
import com.sysbeckysfloristeria.g3.main.service.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping("/alluser")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(service.getAllUser());
    }

    @PostMapping("/saveuser")
    public ResponseEntity<String> saveUser(@Valid @RequestBody UserSaveDto userSaveDto) {
        try {
            service.saveUser(userSaveDto);
            return ResponseEntity.status(HttpStatus.CREATED).body("Usuario guardado con éxito");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @PutMapping("/edituser")
    public String editUser(@RequestBody UserEditDto userEditDto){
        service.editUser(userEditDto);
        return "user edited";
    }

    @GetMapping("/iduser/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        return ResponseEntity.ok(
                service.findById(id).orElseThrow(() -> new ResourceNotFoundException("Usuario con ID " + id + " no encontrado"))
        );
    }

    @PostMapping("/worduser")
    public ResponseEntity<List<UserDto>> findByWord(@RequestBody Map<String, String> request) {
        String word = request.get("word");
        return ResponseEntity.ok(service.dinByWord(word));
    }

    @DeleteMapping("/deletid/{id}")
    public ResponseEntity<String> deleteById(@PathVariable Long id) {
        return ResponseEntity.ok(service.deletById(id));
    }}
