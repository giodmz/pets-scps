package com.pets.config;

import com.pets.entities.Address;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
import com.pets.repository.AddressRepository;
import com.pets.repository.PetRepository;
import com.pets.services.PetService;
import com.pets.services.exceptions.InputException;
import com.pets.services.exceptions.ObjectNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MenuHandler {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PetService service;

    public void mainMenu() {

        Locale.setDefault(Locale.US);
        Scanner sc = new Scanner(System.in);

        try {
            System.out.println("\n1 - Cadastrar novo pet");
            System.out.println("2 - Alterar dados do pet cadastrado");
            System.out.println("3 - Deletar um pet cadastrado");
            System.out.println("4 - Listar todos os pets cadastrados");
            System.out.println("5 - Listar pets por algum critério");
            System.out.println("6 - Sair");
            Integer input = sc.nextInt();

            switch (input) {
                case 1:
                    registerPetMenu();
                    break;
                case 2:
//                    alterarDadosPetMenu();
                    break;
                case 3:
//                    removerPetMenu();
                    break;
                case 4:
                    listAllPetsMenu();
                    break;
                case 5:
//                    menuBuscarDados();
                    break;
                case 6:
                    System.out.println("\nAté logo...");
                    System.exit(0);
                default:
                    break;
            }

            if (input <= 0) {
                throw new InputException("Por insira um número válido.");
            }

        } catch (InputMismatchException ex) {
            System.out.println("\nFormato inválido, utilize apenas números para navegar.");
            mainMenu();
        } catch (InputException ex) {
            System.out.println("\nErro: " + ex.getMessage());
            mainMenu();
        }

        sc.close();

    }


    public void registerPetMenu() {
        try {
            Scanner sc = new Scanner(System.in);
            System.out.println("Please, insert the pet data:");
            System.out.println("Name:");
            String name = sc.nextLine();

            if (name.isEmpty()) {
                name = "NO_INFO";
            }

            if (name.trim().split("\\s+").length < 2) {
                throw new InputException("Please insert first and last name.");
            }
            if (!name.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                throw new InputException("The name can't have numbers or special characters");
            }

            System.out.println("Species:");
            String inputSpecies = sc.nextLine();

            System.out.println("Gender");
            String inputSex = sc.nextLine();

            System.out.println("Age:");
            Integer age = sc.nextInt();

            System.out.println("Weight:");
            Double weight = sc.nextDouble();
            sc.nextLine();

            System.out.println("Address:");
            System.out.print("House number: ");
            Integer num = sc.nextInt();
            sc.nextLine();
            System.out.print("City: ");
            String city = sc.nextLine();

            System.out.print("Street: ");
            String street = sc.nextLine();

            Address address = Address.builder()
                    .num(num)
                    .city(city)
                    .street(street)
                    .build();
            addressRepository.save(address);


            if (age >= 22) {
                throw new InputException("Please, insert a valid age");
            }

            if (!inputSpecies.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                throw new InputException("O name não pode conter números ou caracteres especiais.");
            }

            Species specie = Species.valueOf(inputSpecies.toUpperCase());
            Gender sex = Gender.valueOf(inputSex.toUpperCase());

            Pet pet = Pet.builder()
                    .name(name)
                    .gender(sex)
                    .age(age)
                    .weight(weight)
                    .species(specie)
                    .address(address)
                    .build();
            service.insert(pet);

            System.out.println("Your pet is successful registered: " + pet.toString());

            sc.close();

        } catch (InputException ex) {
            ex.getMessage();
        }

        returnToMainMenu();

    }

    public void listAllPetsMenu() {
        try {
            List<Pet> pets = service.findAll();

            for (Pet pet : pets) {
                System.out.println(pet.toString());
            }

        } catch (ObjectNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            mainMenu();
        }

        returnToMainMenu();

    }

    public void returnToMainMenu() {
        Scanner sc = new Scanner(System.in);

        System.out.println("\nDo you want to return to main menu? (y/n)");
        String input = sc.next();
        if (Objects.equals(input, "y")) {
            mainMenu();
        } else {
            System.out.println("See u... :)");
            System.exit(0);
        }

    }
}

