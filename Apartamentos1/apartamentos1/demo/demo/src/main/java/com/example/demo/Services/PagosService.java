package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.PagosModel;
import com.example.demo.Repositories.IPagosRepository;

@Service
public class PagosService {
    
    @Autowired
    private IPagosRepository paymentTransactionRepository;

    public List<PagosModel> fetchAllTransactions() {
        return paymentTransactionRepository.findAll();
    }

    public Optional<PagosModel> findTransactionById(Long id) {
        return paymentTransactionRepository.findById(id);
    }

    public PagosModel persistTransactionData(PagosModel transactionData) {
        return paymentTransactionRepository.save(transactionData);
    }

    public void removeTransactionById(Long id) {
        paymentTransactionRepository.deleteById(id);
    }

    
}
