package com.example.demo.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.Models.ResenasModel;
import com.example.demo.Repositories.IResenasRepository;

@Service
public class ResenasService {
    
    @Autowired
    private IResenasRepository reviewRepository;

    public List<ResenasModel> fetchAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<ResenasModel> findReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public ResenasModel persistReviewData(ResenasModel reviewData) {
        return reviewRepository.save(reviewData);
    }

    public void removeReviewById(Long id) {
        reviewRepository.deleteById(id);
    }


}
