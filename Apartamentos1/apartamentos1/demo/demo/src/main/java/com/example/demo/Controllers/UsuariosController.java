package com.example.demo.Controllers;

import java.util.List;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.UsuariosModel;
import com.example.demo.Services.UsuariosService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api/v1/auth/users")
public class UsuariosController {
    @Autowired
    private UsuariosService userManagementService;

    //Get all usuarios
    @GetMapping()
    public List<UsuariosModel> fetchAllUsers(){
        return userManagementService.findAllUsers();
    }

    //Get usuario by id
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosModel> fetchUserById(@PathVariable Long id){
        Optional<UsuariosModel> userRecord = userManagementService.findUserById(id);
        return userRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create usuario
    @PostMapping
    public UsuariosModel registerNewUser(@RequestBody UsuariosModel userData){
        return userManagementService.persistNewUser(userData);
    }

    //Update usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuariosModel> modifyUserData(@PathVariable Long id, @RequestBody UsuariosModel userData){
    Optional<UsuariosModel> existingUserData = userManagementService.findUserById(id);

    if (existingUserData.isPresent()) {
        UsuariosModel userToModify = existingUserData.get();
        userToModify.setFullName(userData.getFullName());
        userToModify.setFatherName(userData.getFatherName());
        userToModify.setMotherName(userData.getMotherName());
        userToModify.setEmailAddress(userData.getEmailAddress());
        userToModify.setMobilePhone(userData.getMobilePhone());
        userToModify.setBirthDate(userData.getBirthDate());
        userToModify.setIdentificationNumber(userData.getIdentificationNumber());
        userToModify.setHomeAddress(userData.getHomeAddress());
        userToModify.setUserProfileType(userData.getUserProfileType());
        userToModify.setAccountStatus(userData.getAccountStatus());
        userToModify.setEncryptedPassword(userData.getEncryptedPassword());

        UsuariosModel modifiedUser = userManagementService.persistNewUser(userToModify);
        return ResponseEntity.ok(modifiedUser);
    } else {
        return ResponseEntity.notFound().build();
    }
    }

    //Delete usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeUserFromSystem(@PathVariable Long id){
        Optional<UsuariosModel> existingUserData = userManagementService.findUserById(id);
        if (existingUserData.isPresent()) {
            userManagementService.removeUserById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
