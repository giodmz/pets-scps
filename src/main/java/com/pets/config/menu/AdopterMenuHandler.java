package com.pets.config.menu;

import com.pets.entities.Address;
import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.enums.Status;
import com.pets.exceptions.InputException;
import com.pets.exceptions.ObjectNotFoundException;
import com.pets.repository.AddressRepository;
import com.pets.repository.AdopterRepository;
import com.pets.services.AdopterService;
import com.pets.services.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class AdopterMenuHandler {

    @Autowired
    public AdopterMenuHandler(Scanner sc) {
        this.sc = sc;
    }

    private final Scanner sc;

    @Autowired
    private AdopterRepository adopterRepository;

    @Autowired
    private AdopterService adopterService;

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private PetService petService;

    public void adopterMainMenu() {

        Locale.setDefault(Locale.US);
        try {
            System.out.println("\n1 - Register a new adopter");
            System.out.println("2 - Change adopter data");
            System.out.println("3 - Delete a adopter from database");
            System.out.println("4 - List all registered adopters");
            System.out.println("5 - Find adopter by name");
            System.out.println("6 - Exit");
            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    registerAdopterMenu();
                    break;
                case 2:
                    modifyAdopterDataMenu();
                    break;
                case 3:
                    deleteAdopterMenu();
                    break;
                case 4:
                    listAllAdoptersMenu();
                    break;
                case 5:
                    findAdopterByNameMenu();
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
            adopterMainMenu();
        } catch (InputException ex) {
            System.out.println("\nError: " + ex.getMessage());
            adopterMainMenu();
        }

    }

    public void adoptProcessPetMenu(){
        System.out.println("Enter your e-mail: ");
        String email = sc.nextLine();

        try {
            Adopter adopter = adopterService.findByEmail(email);
            System.out.println("Welcome " + adopter.getName());

            System.out.println("Please, enter the pet name do you want to adopt: ");
            String petName = sc.nextLine();
            List<Pet> availablePet = petService.findAvailableToAdoptPet(petName);

            if (availablePet.isEmpty()) {
                System.out.println("We have no pets available with this name");
                System.out.println("\nPlease check our available pets:");
                for (Pet pet : petService.listAvailableToAdoptPet()) {
                    System.out.println(pet);
                }

                returnToAdopterMainMenu();
            } else {
                System.out.println("Available Pets:");
                System.out.println(availablePet.toString());
            }

            System.out.println("Enter the pet ID to adopt: ");
            Integer petId = sc.nextInt();
            Pet findPet = petService.findById(petId);

            petService.adoptionProcess(findPet, adopter);


        } catch (ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }
    }

    public void registerAdopterMenu() {
        try {
            System.out.println("Please, insert the adopter data:");
            System.out.println("Name:");
            String name = sc.nextLine();

            if (name == null || name.trim().isEmpty()
                    || name.trim().split("\\s+").length < 2) {
                throw new InputException("Please insert first and last name.");
            }

            if (!name.matches("^[A-Za-zÀ-ÿ\\s]+$")) {
                throw new InputException("The name can't have numbers or special characters");
            }

            System.out.println("E-mail:");
            String email = sc.nextLine();

            System.out.println("Phone number:");
            String contact = sc.nextLine();

            System.out.print("City: ");
            String city = sc.nextLine();
            System.out.print("Street: ");
            String street = sc.nextLine();
            System.out.print("Number: ");
            Integer num = sc.nextInt();
            sc.nextLine();

            Address address = Address.builder()
                    .city(city)
                    .street(street)
                    .num(num)
                    .build();

            Adopter adopter = Adopter.builder()
                    .name(name)
                    .email(email)
                    .contact(contact)
                    .address(address)
                    .pets(null)
                    .build();

            addressRepository.save(address);
            adopterService.insert(adopter);

            System.out.println("You have been successfully registered: " + adopter.toString());


        } catch (InputException ex) {
            System.out.println("Error: " + ex.getMessage());
        }

        returnToAdopterMainMenu();

    }

    public void listAllAdoptersMenu() {
        try {
            List<Adopter> adopters = adopterService.findAll();

            System.out.println("Adopters found on database: ");
            for (Adopter adopter : adopters) {

                System.out.println(adopter.toString());
            }

        } catch (ObjectNotFoundException ex) {
            System.out.println("Error: " + ex.getMessage());
            adopterMainMenu();
        }

        returnToAdopterMainMenu();

    }

    public  void deleteAdopterMenu(){
        findAdopterByName();

        Scanner sc = new Scanner(System.in);
        System.out.print("Insert the adopter ID you want to delete from database: ");
        Integer id = sc.nextInt();
        sc.nextLine();


        try {
            Adopter adopter = adopterService.findById(id);
            System.out.println("Type DELETE to confirm that" + adopter.getName() + " will be deleted FOREVER");
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid ID");
        } catch (ObjectNotFoundException e) {
            System.out.println(e.getMessage());
        }


        String confirmation = sc.nextLine();
        if (confirmation.equalsIgnoreCase("DELETE")) {
            try {
                adopterService.delete(id);
            } catch (ObjectNotFoundException ex) {
                ex.getMessage();
                returnToAdopterMainMenu();
            }

            System.out.println("Successfully deleted.");
            returnToAdopterMainMenu();
        } else {
            System.out.println("Deleting process canceled.");
            returnToAdopterMainMenu();
        }

    }

    public void findAdopterByNameMenu() {
        findAdopterByName();
    }

    public  void modifyAdopterDataMenu() {
        findAdopterByName();

        Scanner sc = new Scanner(System.in);
        System.out.print("Insert the adopter ID you want to change data: ");
        Integer id = sc.nextInt();
        sc.nextLine();

        Adopter adopter = adopterService.findById(id);
        if (adopter == null) {
            System.out.println("Adopter not found!");
            return;
        }

        System.out.println("\nWhat do you want to change?");
        System.out.println("1 - Name");
        System.out.println("2 - E-mail");
        System.out.println("3 - Contact");
        System.out.println("4 - Address");
        System.out.println("0 - Exit");

        int input = sc.nextInt();
        sc.nextLine();

        switch (input) {
            case 1:
                System.out.print("New name: ");
                String name = sc.nextLine();
                adopter.setName(name);
                break;
            case 2:
                System.out.print("New e-mail: ");
                String email = sc.nextLine();
                adopter.setEmail(email);
                break;
            case 3:
                System.out.print("New contact: ");
                String contact = sc.nextLine();
                adopter.setContact(contact);
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
                addressRepository.save(newAddress);
                adopter.setAddress(newAddress);
                break;
            case 0:
                System.out.println("Exiting...");
                return;
            default:
                System.out.println("Invalid option!");
                return;
        }

        adopterService.update(adopter);
        System.out.println("Adopter data updated successfully.");

        returnToAdopterMainMenu();
    }

    public void findAdopterByName() {
        try {
            Scanner sc = new Scanner(System.in);

            System.out.println("Insert your adopter name: ");
            String name = sc.nextLine();
            List<Adopter> adopters = adopterService.findByNameLike(name);
            if (adopters.isEmpty()) {
                System.out.println("\nNo data found on database");
                returnToAdopterMainMenu();
            }
            System.out.println("\nAdopters with that name found on database: ");
            for (Adopter adopter : adopters) {
                System.out.println(adopter.toString());
            }


        } catch (ObjectNotFoundException ex){
            ex.getMessage();
        }

    }

    public void returnToAdopterMainMenu() {
        System.out.println("\nDo you want to return to main menu? (y/n)");
        String input = sc.next();
        if (Objects.equals(input, "y")) {
            adopterMainMenu();
        } else {
            System.out.println("See u... :)");
            sc.close();
            System.exit(0);
        }

    }

}
