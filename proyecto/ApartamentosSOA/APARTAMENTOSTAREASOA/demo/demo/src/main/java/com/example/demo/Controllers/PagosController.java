package com.example.demo.Controllers;

import java.util.List;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Models.PagosModel;
import com.example.demo.Services.PagosService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/v1/payments/transactions")
public class PagosController {
    
    @Autowired
    private PagosService paymentProcessingService;

    //GET all pagos
    @GetMapping()
    public List<PagosModel> fetchAllTransactions(){
        return paymentProcessingService.fetchAllTransactions();
    }

    //Get pago by id
    @GetMapping("/{id}")
    public ResponseEntity<PagosModel> fetchTransactionById(@PathVariable Long id){
        Optional<PagosModel> transactionRecord = paymentProcessingService.findTransactionById(id);
        return transactionRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create pago
    @PostMapping
    public PagosModel registerNewTransaction(@RequestBody PagosModel transactionData){
        return paymentProcessingService.persistTransactionData(transactionData);
    }

    //Update pago
    @PutMapping("/{id}")
    public ResponseEntity<PagosModel> modifyTransactionRecord(@PathVariable Long id, @RequestBody PagosModel transactionData){
        Optional<PagosModel> existingTransaction = paymentProcessingService.findTransactionById(id);

        if (existingTransaction.isPresent()) {
            PagosModel recordToModify = existingTransaction.get();
            recordToModify.setTransactionAmount(transactionData.getTransactionAmount());
            recordToModify.setPaymentInstrument(transactionData.getPaymentInstrument());
            recordToModify.setTransactionStatus(transactionData.getTransactionStatus());
            recordToModify.setConfirmationCode(transactionData.getConfirmationCode());
            recordToModify.setProcessorMetadata(transactionData.getProcessorMetadata());

            PagosModel modifiedRecord = paymentProcessingService.persistTransactionData(recordToModify);
            return ResponseEntity.ok(modifiedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    //Delete pago
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeTransactionById(@PathVariable Long id){
         Optional<PagosModel> existingTransaction = paymentProcessingService.findTransactionById(id);
        if (existingTransaction.isPresent()) {
             paymentProcessingService.removeTransactionById(id);
             return ResponseEntity.noContent().build();
        } else {
                return ResponseEntity.notFound().build();
        }
    }


   
    
     
}
