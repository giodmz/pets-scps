package com.pets.config;

import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
import com.pets.enums.Status;
import com.pets.repository.AddressRepository;
import com.pets.repository.AdopterRepository;
import com.pets.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@Configuration
public class Instantiation implements CommandLineRunner {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AdopterRepository adopterRepository;


    @Override
    public void run(String... args) throws Exception {

            petRepository.deleteAll();
            adopterRepository.deleteAll();
            addressRepository.deleteAll();

            Address address1 = Address.builder()
                    .num(13)
                    .city("BH")
                    .street("SF3TS")
                    .build();

        Address address2 = Address.builder()
                .num(21)
                .city("SP")
                .street("SF6")
                .build();

            addressRepository.save(address1);
            addressRepository.save(address2);

            Pet pet1 = Pet.builder()
                    .name("Shadowheart")
                    .gender(Gender.FEMALE)
                    .age(20)
                    .weight(30.0)
                    .species(Species.CAT)
                    .status(Status.AVAILABLE)
                    .adopter(null)
                    .build();

            Pet pet2 = Pet.builder()
                    .name("Lae'zel")
                    .gender(Gender.FEMALE)
                    .age(5)
                    .weight(22.5)
                    .species(Species.DOG)
                    .status(Status.AVAILABLE)
                    .adopter(null)
                    .build();

            Pet pet3 = Pet.builder()
                    .name("Astarion")
                    .gender(Gender.MALE)
                    .age(8)
                    .weight(15.0)
                    .species(Species.CAT)
                    .status(Status.AVAILABLE)
                    .adopter(null)
                    .build();

            Adopter adopter1 = Adopter.builder()
                    .name("Thrall")
                    .email("thrall@gmail.com")
                    .contact("(99)9 9999-9999")
                    .address(address1)
                    .pets(new ArrayList<>())
                    .build();

            Adopter adopter2 = Adopter.builder()
                    .name("Sylvanas")
                    .email("windrunner@gmail.com")
                    .contact("(33)3 3333-3333")
                    .address(address2)
                    .pets(new ArrayList<>())
                    .build();

        petRepository.saveAll(Arrays.asList(pet1, pet2, pet3));
        adopterRepository.saveAll(Arrays.asList(adopter1, adopter2));

        adoptionProcess(pet1, adopter1);
        adoptionProcess(pet3, adopter1);
        adoptionProcess(pet2, adopter2);

        System.out.println("------------------------System starting-------------------------");

        }

    private void adoptionProcess(Pet pet, Adopter adopter) {
        pet.setAdopter(adopter);
        pet.setStatus(Status.ADOPTED);

        petRepository.save(pet);

        System.out.println(pet.getName() + " - " + adopter.getName());
    }

    }


