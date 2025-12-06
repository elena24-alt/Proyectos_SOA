package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.MensajesModel;
import com.example.demo.Repositories.IMensajesRepository;

@Service
public class MensajesService {
    
    @Autowired
    private IMensajesRepository messagingRepository;

    public List<MensajesModel> fetchAllMessages() {
        return messagingRepository.findAll();
    }

    public Optional<MensajesModel> findMessageById(Long id) {
        return messagingRepository.findById(id);
    }

    public MensajesModel persistMessageData(MensajesModel messageData) {
        return messagingRepository.save(messageData);
    }

    public void removeMessageById(Long id) {
        messagingRepository.deleteById(id);
    }

}
