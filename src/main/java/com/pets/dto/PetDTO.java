package com.pets.dto;

import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
import com.pets.enums.Status;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class PetDTO implements Serializable {

    Integer id;
    String name;
    Gender gender;
    Integer age;
    Double weight;
    Species species;
    Adopter adopter;
    Status status;

    public PetDTO(Pet obj) {
        id = obj.getId();
        name = obj.getName();
        gender = obj.getGender();
        age = obj.getAge();
        weight = obj.getWeight();
        species = obj.getSpecies();
        adopter = obj.getAdopter();
        status = obj.getStatus();
    }

}
