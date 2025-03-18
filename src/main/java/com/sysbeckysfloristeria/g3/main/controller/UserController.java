package com.sysbeckysfloristeria.g3.main.controller;

import com.sysbeckysfloristeria.g3.main.model.User;
import com.sysbeckysfloristeria.g3.main.modelDTO.UserDTO;
import com.sysbeckysfloristeria.g3.main.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user/v1")
public class UserController {
    @Autowired
    private IUserService service;

    @GetMapping("/alluser")
    public List<UserDTO> getAllUser(){
        return service.getAllUser();
    }

    @PostMapping("/saveuser")
    public User saveUser(@RequestBody User user){
        return service.saveUser(user);
    }

    @PutMapping("/edituser")
    public User editUser(@RequestBody User user){
        return service.editUser(user);
    }

    @GetMapping("/iduser/{id}")
    public Optional<User> findbyId(@PathVariable Long id){
        return service.findById(id);
    }

    @PostMapping("/worduser")
    public List<UserDTO> findByWord(@RequestBody Map<String, String> request){
        String word= request.get("word");
        return service.dinByWord(word);
    }

    @DeleteMapping("/deletid/{id}")
    public String deletById(@PathVariable Long id){
        return service.deletById(id);
    }
}
