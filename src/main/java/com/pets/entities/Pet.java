package com.pets.entities;

import com.pets.enums.Gender;
import com.pets.enums.Species;
import jakarta.persistence.*;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Entity
public class Pet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer id;

    String name;
    Gender gender;
    Integer age;
    Double weight;
    Species species;

    @ManyToOne
    Address address;

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Gender: %s | Age: %d | Weight: %.2f kg | Species: %s | Address: %s",
                id,
                name,
                formatEnum(gender),
                age,
                weight,
                formatEnum(species),
                formatAddress(address)
        );
    }

    private String formatEnum(Enum<?> e) {
        return e != null ? e.name().toLowerCase() : "unknown";
    }

    private String formatAddress(Address address) {
        if (address == null) return "No address";

        return String.format("%s, %s - %s",
                address.getStreet(),
                address.getNum(),
                address.getCity()
        );
    }
}
