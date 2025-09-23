package com.pets.config;

import com.pets.repository.AddressRepository;
import com.pets.repository.AdopterRepository;
import com.pets.services.AdopterService;
import com.pets.services.PetService;
import com.pets.exceptions.InputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class MenuHandler {


    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private AdopterRepository adopterRepository;

    @Autowired
    private PetService petService;

    @Autowired
    private AdopterService adopterService;

    @Autowired
    private final PetMenuHandler petMenuHandler;

    public MenuHandler(PetMenuHandler petMenuHandler) {
        this.petMenuHandler = petMenuHandler;
    }

    Scanner sc = new Scanner(System.in);

    public void initialMenu(){

        try {
            System.out.println("Adoption System");
            System.out.println("\n1 - Adopter menu");
            System.out.println("2 - Pet menu");
            int input = sc.nextInt();
            switch (input) {
                case 1:
                    petMenuHandler.petMainMenu();
                    break;
                case 2:
                    adopterMainMenu();
                    break;

            }
        }catch (InputException ex) {
            ex.getMessage();
        }
    }

    public void adopterMainMenu(){
        Locale.setDefault(Locale.US);

        try {
            System.out.println("\n1 - Register");
            System.out.println("2 - Change data");
            System.out.println("3 - Delete");
            System.out.println("4 - List all registered adopters");
            System.out.println("5 - Find adopter by name");
            System.out.println("6 - Exit");
            int input = sc.nextInt();

            switch (input) {
                case 1:
//                    registerAdopterMenu();
                    break;
                case 2:
//                    modifyPetDataMenu();
                    break;
                case 3:
//                    deletePetMenu();
                    break;
                case 4:
//                    listAllPetsMenu();
                    break;
                case 5:
//                    findPetByNameMenu();
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
//            petMainMenu();
        } catch (InputException ex) {
            System.out.println("\nError: " + ex.getMessage());
//            petMainMenu();
        }

    }


}

