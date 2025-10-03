package com.pets.services;


import com.pets.dto.AdopterDTO;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.enums.Status;
import com.pets.exceptions.ObjectNotFoundException;
import com.pets.repository.AdopterRepository;
import com.sun.jdi.ObjectCollectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdopterService {
    @Autowired
    private AdopterRepository rep;

    public List<Adopter> findAll() {
        return rep.findAll();
    }

    public Adopter findById(Integer id) {
        requiredValidId(id);
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid ID: " + id);
        }
        return rep.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Adopter not found. (Id: " + id + ")"));
    }


    public void insert(Adopter obj) {
        rep.save(obj);
    }

    public void delete(Integer id){
        requiredValidId(id);
        rep.findById(id).orElseThrow(ObjectCollectedException::new);
        rep.deleteById(id);
    }

    public Adopter update(Adopter obj) {
        requiredValidId(obj.getId());
        Adopter newObj = rep.findById(obj.getId()).orElseThrow(ObjectCollectedException::new);
        updateData(newObj, obj);
        return rep.save(newObj);
    }

    private void updateData(Adopter newObj, Adopter obj) {
        newObj.setName(obj.getName());
        newObj.setEmail(obj.getEmail());
        newObj.setContact(obj.getContact());
        newObj.setAddress(obj.getAddress());
        newObj.setPets(obj.getPets());
    }

    public Adopter fromDTO (AdopterDTO objDto) {
        return new Adopter(objDto.getId(),
                objDto.getName(),
                objDto.getEmail(),
                objDto.getContact(),
                objDto.getAddress(),
                objDto.getPets());
    }

    public List<Adopter> findByNameLike(String name){
        return  rep.findByNameLike(name);
    }

    private void requiredValidId(Integer id){
        if (id <= 0){
            throw new IllegalArgumentException("Invalid id");
        }
    }

}
