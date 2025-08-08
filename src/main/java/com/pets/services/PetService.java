package com.pets.services;

import com.pets.entities.Pet;
import com.pets.repository.PetRepository;
import com.sun.jdi.ObjectCollectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PetService {

    @Autowired
    private PetRepository rep;

    public List<Pet> findAll() {
        return rep.findAll();

    }

    public Pet findById(Integer id){
        return rep.findById(id).orElseThrow(ObjectCollectedException::new);
    }


    public void insert(Pet obj) {
        rep.save(obj);
    }

    public void delete(Integer id){
        rep.findById(id).orElseThrow(ObjectCollectedException::new);
        rep.deleteById(id);
    }

    public Pet update(Pet obj) {
        Pet newObj = rep.findById(obj.getId()).orElseThrow(ObjectCollectedException::new);
        updateData(newObj, obj);
        return rep.save(newObj);
    }

    private void updateData(Pet newObj, Pet obj) {
        newObj.setName(obj.getName());
        newObj.setAge(obj.getAge());
        newObj.setSpecies(obj.getSpecies());
        newObj.setWeight(obj.getWeight());
        newObj.setAddress(obj.getAddress());
    }

}
