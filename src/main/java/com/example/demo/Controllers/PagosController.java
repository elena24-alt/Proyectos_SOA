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

import com.example.demo.Models.PagosModel;
import com.example.demo.Services.PagosService;


@RestController
@RequestMapping("/pagos")
public class PagosController {
    
    @Autowired
    private PagosService pagosService;

    //GET all pagos
    @GetMapping()
    public List<PagosModel> getAllPagos(){
        return pagosService.getAllPagos();
    }

    //Get pago by id
    @GetMapping("/{id}")
    public ResponseEntity<PagosModel> getPagoById(@PathVariable Long id){
        Optional<PagosModel> pago = pagosService.getPagoById(id);
        return pago.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create pago
    @PostMapping
    public PagosModel createPago(@RequestBody PagosModel pago){
        return pagosService.savePago(pago);
    }

    //Update pago
    @PutMapping("/{id}")
    public ResponseEntity<PagosModel> updatePago(@PathVariable Long id, @RequestBody PagosModel pago){
        Optional<PagosModel> existingPago = pagosService.getPagoById(id);

        if (existingPago.isPresent()) {
            PagosModel pagoToUpdate = existingPago.get();
            pagoToUpdate.setMonto(pago.getMonto());
            pagoToUpdate.setMetodoPago(pago.getMetodoPago());
            pagoToUpdate.setEstado(pago.getEstado());
            pagoToUpdate.setReferenciaPago(pago.getReferenciaPago());
            pagoToUpdate.setDatosPago(pago.getDatosPago());

            PagosModel updatedPago = pagosService.savePago(pagoToUpdate);
            return ResponseEntity.ok(updatedPago);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Delete pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePago(@PathVariable Long id){
         Optional<PagosModel> existingPago = pagosService.getPagoById(id);
        if (existingPago.isPresent()) {
             pagosService.deletePago(id);
             return ResponseEntity.noContent().build();
        } else {
                return ResponseEntity.notFound().build();
        }
    }


   
    
     
}