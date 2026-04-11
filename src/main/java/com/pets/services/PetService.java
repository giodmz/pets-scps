package com.pets.services;

import com.pets.dto.PetDTO;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.enums.Status;
import com.pets.exceptions.ObjectNotFoundException;
import com.pets.repository.PetRepository;
import com.sun.jdi.ObjectCollectedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;

@Service
public class PetService {

    @Autowired
    private PetRepository rep;

    public List<Pet> findAll() {
        return rep.findAll();
    }

    public Pet findById(Integer id) {
        requiredValidId(id);
        return rep.findById(id)
                .orElseThrow(new Supplier<ObjectNotFoundException>() {
                    @Override
                    public ObjectNotFoundException get() {
                        return new ObjectNotFoundException("Pet not found. (Id: " + id + ")");
                    }
                });
    }



    public List<Pet> findByNameLike(String name){
        return  rep.findByNameLike(name);
    }

    public List<Pet> findAvailableToAdoptPet(String name) { return rep.findAvailableToAdoptPet(name); }

    public List<Pet> listAvailableToAdoptPet() { return  rep.listAvailableToAdoptPet(); }

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
        newObj.setAdopter(obj.getAdopter());
    }

    public Pet fromDTO (PetDTO objDto) {
        return new Pet(objDto.getId(),
                objDto.getName(),
                objDto.getGender(),
                objDto.getAge(),
                objDto.getWeight(),
                objDto.getSpecies(),
                objDto.getStatus(),
                objDto.getAdopter());
    }

    private void requiredValidId(Integer id) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (id <= 0) {
            throw new IllegalArgumentException("Invalid id: " + id);
        }
    }

    public void adoptionProcess(Pet pet, Adopter adopter) {
        pet.setAdopter(adopter);
        pet.setStatus(Status.ADOPTED);

        rep.save(pet);

        System.out.println(pet.getName() + " has been adopted by: " + adopter.getName());
    }

}
