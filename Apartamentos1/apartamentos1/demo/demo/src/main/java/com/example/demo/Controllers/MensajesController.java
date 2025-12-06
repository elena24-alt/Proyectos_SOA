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
@RequestMapping("/api/v1/messaging/messages")
public class MensajesController {
    
    @Autowired
    private MensajesService messageManagementService;

    //Get all mensajes
    @GetMapping()
    public List<MensajesModel> fetchAllMessages(){
        return messageManagementService.fetchAllMessages();
    }

    //Get mensaje by id
    @GetMapping("/{id}")
    public ResponseEntity<MensajesModel> fetchMessageById(@PathVariable Long id){
        Optional<MensajesModel> messageRecord = messageManagementService.findMessageById(id);
        return messageRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create mensaje
    @PostMapping
    public MensajesModel registerNewMessage(@RequestBody MensajesModel messageData){
        return messageManagementService.persistMessageData(messageData);
    }

    //Update mensaje
    @PutMapping("/{id}")
    public ResponseEntity<MensajesModel> modifyMessageRecord(@PathVariable Long id, @RequestBody MensajesModel messageData){
        Optional<MensajesModel> existingMessage = messageManagementService.findMessageById(id);

        if (existingMessage.isPresent()) {
            MensajesModel recordToModify = existingMessage.get();
            recordToModify.setMessageSender(messageData.getMessageSender());
            recordToModify.setMessageRecipient(messageData.getMessageRecipient());
            recordToModify.setMessageSubject(messageData.getMessageSubject());
            recordToModify.setMessageContent(messageData.getMessageContent());
            recordToModify.setIsRead(messageData.getIsRead());

            MensajesModel modifiedRecord = messageManagementService.persistMessageData(recordToModify);
            return ResponseEntity.ok(modifiedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete mensaje
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeMessageById(@PathVariable Long id){
        Optional<MensajesModel> existingMessage = messageManagementService.findMessageById(id);

        if (existingMessage.isPresent()) {
            messageManagementService.removeMessageById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    
}
