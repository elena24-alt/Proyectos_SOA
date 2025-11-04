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

import com.example.demo.Models.ResenasModel;
import com.example.demo.Services.ResenasService;

@RestController
@RequestMapping("/resenas")
public class ResenasController {
    
    @Autowired
    private ResenasService resenasService;

    //Get all resenas
    @GetMapping()
    public List<ResenasModel> getAllResenas(){
        return resenasService.getAllResenas();
    }

    //Get resena by id
    @GetMapping("/{id}")
    public ResponseEntity<ResenasModel> getResenaById(@PathVariable Long id){
        Optional<ResenasModel> resena = resenasService.getResenaById(id);
        return resena.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create resena
    @PostMapping
    public ResenasModel createResena(@RequestBody ResenasModel resena){
        return resenasService.saveResena(resena);
    }

    //Update resena
    @PutMapping("/{id}")
    public ResponseEntity<ResenasModel> updateResena(@PathVariable Long id, @RequestBody ResenasModel resena){
        Optional<ResenasModel> existingResena = resenasService.getResenaById(id);

        if (existingResena.isPresent()) {
            ResenasModel resenaToUpdate = existingResena.get();
            resenaToUpdate.setCalificacionLimpieza(resena.getCalificacionLimpieza());
            resenaToUpdate.setCalificacionUbicacion(resena.getCalificacionUbicacion());
            resenaToUpdate.setCalificacionComunicacion(resena.getCalificacionComunicacion());
            resenaToUpdate.setCalificacionGeneral(resena.getCalificacionGeneral());
            resenaToUpdate.setComentario(resena.getComentario());
            resenaToUpdate.setRespuestaPropietario(resena.getRespuestaPropietario());

            ResenasModel updatedResena = resenasService.saveResena(resenaToUpdate);
            return ResponseEntity.ok(updatedResena);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete resena
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteResena(@PathVariable Long id){
        Optional<ResenasModel> existingResena = resenasService.getResenaById(id);
        if (existingResena.isPresent()) {
            resenasService.deleteResena(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
