package com.pets.dto;


import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AdopterDTO {

    Integer id;

    @NotBlank(message = "Name is required")
    String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid format")
    String email;

    @NotBlank(message = "Contact is required")
    String contact;

    Address address;
    List<Pet> pets;

    public AdopterDTO (Adopter obj){
        id = obj.getId();
        name = obj.getName();
        email = obj.getEmail();
        contact = obj.getContact();
        address = obj.getAddress();
        pets = obj.getPets();
    }
}
