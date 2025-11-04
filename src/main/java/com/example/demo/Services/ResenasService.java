package com.example.demo.Services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.ResenasModel;
import com.example.demo.Repositories.IResenasRepository;

@Service
public class ResenasService {
    
    @Autowired
    private IResenasRepository resenasRepository;

    public List<ResenasModel> getAllResenas() {
        return resenasRepository.findAll();
    }

    public Optional<ResenasModel> getResenaById(Long id) {
        return resenasRepository.findById(id);
    }

    public ResenasModel saveResena(ResenasModel resena) {
        return resenasRepository.save(resena);
    }

    public void deleteResena(Long id) {
        resenasRepository.deleteById(id);
    }


}
