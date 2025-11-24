package com.iotfitness.usuarios.Controllers;

import java.util.List; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity; 
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam; 
import org.springframework.web.bind.annotation.RestController;

import com.iotfitness.usuarios.Models.SesionEntrenamiento;
import com.iotfitness.usuarios.Models.UsuariosModel;
import com.iotfitness.usuarios.Repositories.SesionRepository;
import com.iotfitness.usuarios.Repositories.UserRepository; 




@RestController
@RequestMapping("/api/usuarios")
public class UserController {

    @Autowired
    private UserRepository userRepository;
    
    @Autowired 
    private SesionRepository sesionRepository;
    
   
    @PostMapping 
    public UsuariosModel registrarUsuario(@RequestBody UsuariosModel nuevoUsuario) {
        
        return userRepository.save(nuevoUsuario);
    }
    
    @GetMapping 
    public List<UsuariosModel> obtenerTodos() {
        return userRepository.findAll();
    }
    
  

    @PostMapping("/{userId}/sesiones")
    public ResponseEntity<SesionEntrenamiento> registrarSesion(
            @PathVariable Long userId, 
            @RequestBody SesionEntrenamiento nuevaSesion) {
        
        
        return userRepository.findById(userId)
            .map(usuario -> {
               
                nuevaSesion.setUsuario(usuario);
               
                return ResponseEntity.ok(sesionRepository.save(nuevaSesion));
            })
          
            .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{userId}/sesiones")
    public List<SesionEntrenamiento> obtenerSesionesPorUsuario(@PathVariable Long userId) {
    
        return sesionRepository.findByUsuarioId(userId);
    }
    
   

    @PostMapping("/{userId}/frecuencia")
    public String registrarFrecuenciaCardiaca(
            @PathVariable Long userId, 
            @RequestParam int frecuencia) { 
        
    
        String resultadoSOAP = "Frecuencia recibida (" + frecuencia + ") para Usuario " + userId + ". Procesamiento delegado a SOAP (Puerto 8000).";
        
        return resultadoSOAP;
    }
    
}