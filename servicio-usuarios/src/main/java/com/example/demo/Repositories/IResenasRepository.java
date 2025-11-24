package com.example.demo.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.ResenasModel;


@Repository

public interface  IResenasRepository  extends JpaRepository<ResenasModel, Long>{
    
}
