package com.pets.config;

import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
import com.pets.repository.AddressRepository;
import com.pets.repository.AdopterRepository;
import com.pets.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

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

            addressRepository.save(address1);

            Pet pet1 = Pet.builder()
                    .name("Shadowheart")
                    .gender(Gender.FEMALE)
                    .age(20)
                    .weight(30.0)
                    .species(Species.CAT)
                    .build();

            Pet pet2 = Pet.builder()
                    .name("Lae'zel")
                    .gender(Gender.FEMALE)
                    .age(5)
                    .weight(22.5)
                    .species(Species.DOG)
                    .build();

            Pet pet3 = Pet.builder()
                    .name("Astarion")
                    .gender(Gender.MALE)
                    .age(8)
                    .weight(15.0)
                    .species(Species.CAT)
                    .build();

            petRepository.save(pet1);
            petRepository.save(pet2);
            petRepository.save(pet3);

            Adopter adopter1 = Adopter.builder()
                    .name("Thrall")
                    .email("thrall@gmail.com")
                    .contact("(99)9 9999-9999")
                    .address(address1)
                    .pets(Collections.singletonList(pet1))
                    .build();


            adopterRepository.save(adopter1);
        }

    }


