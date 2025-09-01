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
public class Adopter {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    String email;
    String contact;

    @ManyToOne
    Address address;

    @OneToMany(mappedBy = "adopter", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Pet> pets = new ArrayList<>();

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
}
