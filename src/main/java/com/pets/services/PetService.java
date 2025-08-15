package com.pets.services;

import com.pets.dto.PetDTO;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
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
        requiredValidId(id);
        return rep.findById(id).orElseThrow(ObjectCollectedException::new);
    }

    public List<Pet> findByNameLike(String name){
        return  rep.findByNameLike(name);
    }

    public void insert(Pet obj) {
        rep.save(obj);
    }

    public void delete(Integer id){
        requiredValidId(id);
        rep.findById(id).orElseThrow(ObjectCollectedException::new);
        rep.deleteById(id);
    }

    public Pet update(Pet obj) {
        requiredValidId(obj.getId());
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

    public Pet fromDTO (PetDTO objDto) {
        return new Pet(objDto.getId(),
                objDto.getName(),
                objDto.getGender(),
                objDto.getAge(),
                objDto.getWeight(),
                objDto.getSpecies(),
                objDto.getAddress());
    }

    private static void requiredValidId(Integer id){
        if (id <= 0){
            throw new IllegalArgumentException("Invalid id");
        }
    }

}
