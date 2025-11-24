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

import com.example.demo.Models.MensajesModel;
import com.example.demo.Services.MensajesService;

@RestController
@RequestMapping("/mensajes")
public class MensajesController {
    
    @Autowired
    private MensajesService mensajesService;

    //Get all mensajes
    @GetMapping()
    public List<MensajesModel> getAllMensajes(){
        return mensajesService.getAllMensajes();
    }

    //Get mensaje by id
    @GetMapping("/{id}")
    public ResponseEntity<MensajesModel> getMensajeById(@PathVariable Long id){
        Optional<MensajesModel> mensaje = mensajesService.getMensajeById(id);
        return mensaje.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create mensaje
    @PostMapping
    public MensajesModel createMensaje(@RequestBody MensajesModel mensaje){
        return mensajesService.saveMensaje(mensaje);
    }

    //Update mensaje
    @PutMapping("/{id}")
    public ResponseEntity<MensajesModel> updateMensaje(@PathVariable Long id, @RequestBody MensajesModel mensaje){
        Optional<MensajesModel> existingMensaje = mensajesService.getMensajeById(id);

        if (existingMensaje.isPresent()) {
            MensajesModel mensajeToUpdate = existingMensaje.get();
            mensajeToUpdate.setRemitente(mensaje.getRemitente());
            mensajeToUpdate.setDestinatario(mensaje.getDestinatario());
            mensajeToUpdate.setAsunto(mensaje.getAsunto());
            mensajeToUpdate.setContenido(mensaje.getContenido());
            mensajeToUpdate.setLeido(mensaje.getLeido());

            MensajesModel updatedMensaje = mensajesService.saveMensaje(mensajeToUpdate);
            return ResponseEntity.ok(updatedMensaje);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete mensaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMensaje(@PathVariable Long id){
        Optional<MensajesModel> existingMensaje = mensajesService.getMensajeById(id);

        if (existingMensaje.isPresent()) {
            mensajesService.deleteMensaje(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}