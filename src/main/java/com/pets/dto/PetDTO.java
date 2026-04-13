package com.pets.dto;

import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
import com.pets.enums.Status;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@NoArgsConstructor
@Getter
@Setter
public class PetDTO implements Serializable {

    Integer id;

    @NotBlank(message = "Name is required")
    String name;

    @NotNull(message = "Gender is required")
    Gender gender;

    @NotNull(message = "Age is required")
    @Positive(message = "Age must be positive")
    Integer age;

    @NotNull(message = "Weight is required")
    @Positive(message = "Weight must be positive")
    Double weight;

    @NotNull(message = "Species is required")
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
