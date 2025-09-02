package com.pets.dto;


import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@Getter
@Setter
public class AdopterDTO {

    Integer id;
    String name;
    String email;
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
