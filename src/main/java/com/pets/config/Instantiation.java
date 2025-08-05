package com.pets.config;

import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Instantiation implements CommandLineRunner {

//    @Id
//    Integer id;
//
//    String name;
//    Gender gender;
//    Integer age;
//    Double weight;
//    String race;
//    String address;

    @Autowired
    private PetRepository petRepository;


    @Override
    public void run(String... args) throws Exception {

        petRepository.deleteAll();

        Pet rodolfo = Pet.builder().name("Rodolfo")
                .gender(Gender.MALE)
                .age(5).weight(5.3)
                .race("Dobberman")
                .address("rua")
                .build();
        petRepository.save(rodolfo);
    }

}
