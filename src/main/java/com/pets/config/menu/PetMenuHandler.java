package com.pets.config.menu;

import com.pets.entities.Address;
import com.pets.entities.Pet;
import com.pets.enums.Gender;
import com.pets.enums.Species;
import com.pets.exceptions.InputException;
import com.pets.exceptions.ObjectNotFoundException;
import com.pets.repository.PetRepository;
import com.pets.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class PetMenuHandler {


    @Autowired
    public PetMenuHandler(Scanner sc) {
        this.sc = sc;
    }

    private final Scanner sc;

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private PetService petService;

    public void petMainMenu() {

        Locale.setDefault(Locale.US);
        try {
            System.out.println("\n1 - Register a new pet");
            System.out.println("2 - Change pet data");
            System.out.println("3 - Delete a pet from database");
            System.out.println("4 - List all registered pets");
            System.out.println("5 - Find pet by name");
            System.out.println("6 - Exit");
            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    registerPetMenu();
                    break;
                case 2:
                    modifyPetDataMenu();
                    break;
                case 3:
                    deletePetMenu();
                    break;
                case 4:
                    listAllPetsMenu();
                    break;
                case 5:
                    findPetByNameMenu();
                    break;
                case 6:
                    System.out.println("\nAté logo...");
                    System.exit(0);
                default:
                    break;
            }

            if (input <= 0) {
                throw new InputException("Please insert a valid number.");
            }


        } catch (InputMismatchException ex) {
            System.out.println("\nInvalid format, please use only numbers to navigate.");
            petMainMenu();
        } catch (InputException ex) {
            System.out.println("\nError: " + ex.getMessage());
            petMainMenu();
        }

    }


    public void registerPetMenu() {
        try {
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
            sc.nextLine();

            System.out.println("Weight:");
            Double weight = sc.nextDouble();
            sc.nextLine();


            if (age >= 22) {
                throw new InputException("Please, insert a valid age (under 22)");
            }

            if (!inputSpecies.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                throw new InputException("The name can't contain numbers or special characters");
            }

            Species specie = Species.valueOf(inputSpecies.toUpperCase());
            Gender sex = Gender.valueOf(inputSex.toUpperCase());

            Pet pet = Pet.builder()
                    .name(name)
                    .gender(sex)
                    .age(age)
                    .weight(weight)
                    .species(specie)
                    .adopter(null)
                    .build();
            petService.insert(pet);

            System.out.println("Your pet is successful registered: " + pet.toString());


        } catch (InputException ex) {
            ex.getMessage();
        }

        returnToPetMainMenu();

    }

    public void listAllPetsMenu() {
        try {
            List<Pet> pets = petService.findAll();

            System.out.println("Pets found on database: ");
            for (Pet pet : pets) {

                System.out.println(pet.toString());
            }

        } catch (ObjectNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            petMainMenu();
        }

        returnToPetMainMenu();

    }

    public  void deletePetMenu(){
        findPetByName();

        Scanner sc = new Scanner(System.in);
        System.out.print("Insert the pet ID you want to delete from database: ");
        Integer id = sc.nextInt();
        sc.nextLine();


        try {
            Pet pet = petService.findById(id);
            System.out.println("Type DELETE to confirm that" + pet.getName() + " will be deleted FOREVER");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID");
        } catch (ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }


        String confirmation = sc.nextLine();
        if (confirmation.equalsIgnoreCase("DELETE")) {
            try {
                petService.delete(id);
            } catch (ObjectNotFoundException ex) {
                ex.getMessage();
                returnToPetMainMenu();
            }

            System.out.println("Successfully deleted.");
            returnToPetMainMenu();
        } else {
            System.out.println("Deleting process canceled.");
            returnToPetMainMenu();
        }

    }

    public void findPetByNameMenu() {
        findPetByName();
    }

    public  void modifyPetDataMenu() {
        findPetByName();

        Scanner sc = new Scanner(System.in);
        System.out.print("Insert the pet ID you want to change data: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        Pet pet = petService.findById(id);
        if (pet == null) {
            System.out.println("Pet not found!");
            return;
        }

        System.out.println("\nWhat do you want to change?");
        System.out.println("1 - Name");
        System.out.println("2 - Age");
        System.out.println("3 - Weight");
        System.out.println("4 - Address");
        System.out.println("0 - Exit");

        int input = sc.nextInt();
        sc.nextLine();

        switch (input) {
            case 1:
                System.out.print("New name: ");
                String name = sc.nextLine();
                pet.setName(name);
                break;
            case 2:
                System.out.print("New age: ");
                int age = sc.nextInt();
                pet.setAge(age);
                break;
            case 3:
                System.out.print("New weight: ");
                double weight = sc.nextDouble();
                pet.setWeight(weight);
                break;
            case 4:
                System.out.print("New city: ");
                String city = sc.nextLine();
                System.out.print("New street: ");
                String street = sc.nextLine();
                System.out.print("New number: ");
                int num = sc.nextInt();
                Address newAddress = Address.builder()
                        .city(city)
                        .street(street)
                        .num(num)
                        .build();
                break;
            case 0:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid option!");
                return;
        }

        petService.update(pet);
        System.out.println("Pet data updated successfully.");

        returnToPetMainMenu();
    }

    public void findPetByName() {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Insert your pet name: ");
            String name = sc.nextLine();
            List<Pet> pets = petService.findByNameLike(name);
            if (pets.isEmpty()) {
                System.out.println("\nNo data found on database");
                returnToPetMainMenu();
            }
            System.out.println("\nPets with that name found on database: ");
            for (Pet pet : pets) {
                System.out.println(pet.toString());
            }


        } catch (ObjectNotFoundException ex){
            ex.getMessage();
        }

    }

    public void returnToPetMainMenu() {
        System.out.println("\nDo you want to return to main menu? (y/n)");
        String input = sc.next();
        if (Objects.equals(input, "y")) {
            petMainMenu();
        } else {
            System.out.println("See u... :)");
            sc.close();
            System.exit(0);
        }

    }

}
