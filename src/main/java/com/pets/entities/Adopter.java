package com.pets.entities;


import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String email;
    String contact;

    @ManyToOne
    Address address;

    @OneToMany(mappedBy = "adopter", fetch = FetchType.LAZY)
    private List<Pet> pets;

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Email: %s | Contact: %s | Address: %s",
                id,
                name,
                email,
                contact,
                formatAddress(address)

        );
    }

    private String formatAddress(Address address) {
        if (address == null) return "No address";

        return String.format("%s, %s - %s",
                address.getCity(),
                address.getStreet(),
                address.getNum()
        );
    }

    public void addPet(Pet pet) {
        pets.add(pet);
        pet.setAdopter(this);
    }

    public void removePet(Pet pet) {
        pets.remove(pet);
        pet.setAdopter(null);
    }
}
