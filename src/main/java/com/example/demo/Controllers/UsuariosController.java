package com.example.demo.Controllers;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.UsuariosModel;
import com.example.demo.Services.UsuariosService;

@RestController
@RequestMapping ("/usuarios")
public class UsuariosController {
    @Autowired
    private UsuariosService usuariosService;

    //Get all usuarios
    @GetMapping()
    public List<UsuariosModel> getallUsuarios(){
        return usuariosService.getallUsuarios();
    }

    //Get usuario by id
    @GetMapping("/{id}")
    public ResponseEntity<UsuariosModel> getUsuarioById(@PathVariable Long id){
        Optional<UsuariosModel> usuario = usuariosService.getUsuarioById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create usuario
    @PostMapping
    public UsuariosModel createUsuario(@RequestBody UsuariosModel usuario){
        return usuariosService.saveUsuario(usuario);
    }

    //Update usuario
    @PutMapping("/{id}")
    public ResponseEntity<UsuariosModel> updateUsuario(@PathVariable Long id, @RequestBody UsuariosModel usuario){
    Optional<UsuariosModel> existingUsuario = usuariosService.getUsuarioById(id);

    if (existingUsuario.isPresent()) {
        UsuariosModel usuarioToUpdate = existingUsuario.get();
        usuarioToUpdate.setNombres(usuario.getNombres());
        usuarioToUpdate.setApellidoPat(usuario.getApellidoPat());
        usuarioToUpdate.setApellidoMat(usuario.getApellidoMat());
        usuarioToUpdate.setEmail(usuario.getEmail());
        usuarioToUpdate.setTelefono(usuario.getTelefono());
        usuarioToUpdate.setFechaNacimiento(usuario.getFechaNacimiento());
        usuarioToUpdate.setIne(usuario.getIne());
        usuarioToUpdate.setDireccion(usuario.getDireccion());
        usuarioToUpdate.setTipoCliente(usuario.getTipoCliente());
        usuarioToUpdate.setEstatus(usuario.getEstatus());
        usuarioToUpdate.setPassword(usuario.getPassword());

        UsuariosModel updatedUsuario = usuariosService.saveUsuario(usuarioToUpdate);
        return ResponseEntity.ok(updatedUsuario);
    } else {
        return ResponseEntity.notFound().build();
    }
    }

    //Delete usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUsuario(@PathVariable Long id){
        Optional<UsuariosModel> existingUsuario = usuariosService.getUsuarioById(id);
        if (existingUsuario.isPresent()) {
            usuariosService.deleteUsuario(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
