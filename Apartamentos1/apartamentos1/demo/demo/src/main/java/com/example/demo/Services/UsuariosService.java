package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.UsuariosModel;
import com.example.demo.Repositories.IUsuariosRepository;

@Service
public class UsuariosService {
    @Autowired
    private IUsuariosRepository userDataRepository;

    public List<UsuariosModel> findAllUsers(){
        return userDataRepository.findAll();
    }

    public Optional<UsuariosModel> findUserById(Long id){
        return userDataRepository.findById(id);
    }

    public UsuariosModel persistNewUser(UsuariosModel userData){
        return userDataRepository.save(userData);
    }

    public void removeUserById(Long id){
        userDataRepository.deleteById(id);
    }

}
