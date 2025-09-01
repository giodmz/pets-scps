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
    @JoinColumn(name = "adopter_id")
    private Adopter adopter;

    @Override
    public String toString() {
        return String.format(
                "ID: %d | Name: %s | Gender: %s | Age: %d | Weight: %.2f kg | Species: %s | Adopter: %s",
                id,
                name,
                formatEnum(gender),
                age,
                weight,
                formatEnum(species),
                formatAdopter(adopter)
        );
    }

    private String formatEnum(Enum<?> e) {
        return e != null ? e.name().toLowerCase() : "unknown";
    }

    private String formatAdopter(Adopter adopter) {
        if (adopter == null) return "No adopter";

        return String.format("%s, %s - %s",
                adopter.getName(),
                adopter.getEmail(),
                adopter.getContact()
        );
    }
}
