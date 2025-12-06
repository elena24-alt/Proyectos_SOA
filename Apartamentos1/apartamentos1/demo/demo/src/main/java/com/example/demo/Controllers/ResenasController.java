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
@RequestMapping("/api/v1/feedback/reviews")
public class ResenasController {
    
    @Autowired
    private ResenasService reviewManagementService;

    //Get all resenas
    @GetMapping()
    public List<ResenasModel> fetchAllReviews(){
        return reviewManagementService.fetchAllReviews();
    }

    //Get resena by id
    @GetMapping("/{id}")
    public ResponseEntity<ResenasModel> fetchReviewById(@PathVariable Long id){
        Optional<ResenasModel> reviewRecord = reviewManagementService.findReviewById(id);
        return reviewRecord.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    //Create resena
    @PostMapping
    public ResenasModel submitNewReview(@RequestBody ResenasModel reviewData){
        return reviewManagementService.persistReviewData(reviewData);
    }

    //Update resena
    @PutMapping("/{id}")
    public ResponseEntity<ResenasModel> modifyReviewRecord(@PathVariable Long id, @RequestBody ResenasModel reviewData){
        Optional<ResenasModel> existingReview = reviewManagementService.findReviewById(id);

        if (existingReview.isPresent()) {
            ResenasModel recordToModify = existingReview.get();
            recordToModify.setCleanlinessScore(reviewData.getCleanlinessScore());
            recordToModify.setLocationScore(reviewData.getLocationScore());
            recordToModify.setCommunicationScore(reviewData.getCommunicationScore());
            recordToModify.setOverallRating(reviewData.getOverallRating());
            recordToModify.setReviewComment(reviewData.getReviewComment());
            recordToModify.setOwnerResponse(reviewData.getOwnerResponse());

            ResenasModel modifiedRecord = reviewManagementService.persistReviewData(recordToModify);
            return ResponseEntity.ok(modifiedRecord);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //Delete resena
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeReviewById(@PathVariable Long id){
        Optional<ResenasModel> existingReview = reviewManagementService.findReviewById(id);
        if (existingReview.isPresent()) {
            reviewManagementService.removeReviewById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
