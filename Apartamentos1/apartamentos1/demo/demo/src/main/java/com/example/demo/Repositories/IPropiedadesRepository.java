package com.example.demo.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.demo.Models.PropiedadesModel;
import com.example.demo.Models.PropiedadesModel.PropertyType;
import com.example.demo.Models.UsuariosModel;

@Repository
public interface IPropiedadesRepository extends JpaRepository<PropiedadesModel, Long>{

    List<PropiedadesModel> findByPropertyClassification(PropiedadesModel.PropertyType propertyType);

    List<PropiedadesModel> findByPropertyOwner(UsuariosModel ownerUser);
}
