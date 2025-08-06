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

}
