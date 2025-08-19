package com.pets.config;

import com.pets.entities.Address;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
import com.pets.repository.AddressRepository;
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

    @Autowired
    private AddressRepository addressRepository;


    @Override
    public void run(String... args) throws Exception {

        petRepository.deleteAll();

        Address address1 = Address.builder()
                .num(13)
                .city("BH")
                .street("SF3TS")
                .build();

        Address address2 = Address.builder()
                .num(42)
                .city("São Paulo")
                .street("Rua das Rosas")
                .build();

        Address address3 = Address.builder()
                .num(7)
                .city("Rio de Janeiro")
                .street("Avenida das Estrelas")
                .build();

        addressRepository.save(address1);
        addressRepository.save(address2);
        addressRepository.save(address3);

        Pet pet1 = Pet.builder()
                .name("Shadowheart")
                .gender(Gender.FEMALE)
                .age(20)
                .weight(30.0)
                .species(Species.CAT)
                .address(address1)
                .build();

        Pet pet2 = Pet.builder()
                .name("Lae'zel")
                .gender(Gender.FEMALE)
                .age(5)
                .weight(22.5)
                .species(Species.DOG)
                .address(address2)
                .build();

        Pet pet3 = Pet.builder()
                .name("Astarion")
                .gender(Gender.MALE)
                .age(8)
                .weight(15.0)
                .species(Species.CAT)
                .address(address3)
                .build();
        petRepository.save(pet1);
        petRepository.save(pet2);
        petRepository.save(pet3);


    }

}
