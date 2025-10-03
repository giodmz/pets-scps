package com.pets.config.menu;

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

    @Autowired
    private final AdopterMenuHandler adopterMenuHandler;

    public MenuHandler(PetMenuHandler petMenuHandler, AdopterMenuHandler adopterMenuHandler, Scanner sc) {
        this.petMenuHandler = petMenuHandler;
        this.adopterMenuHandler = adopterMenuHandler;
        this.sc = sc;
    }

    private final Scanner sc;

    public void initialMenu(){

        try {
            System.out.println("Adoption System");
            System.out.println("\n1 - Adopt a pet");
            System.out.println("2 - Adopter menu");
            System.out.println("3 - Pet menu");
            int input = sc.nextInt();
            sc.nextLine();

            switch (input) {
                case 1:
                    adopterMenuHandler.adoptProcessPetMenu();
                    break;
                case 2:
                    adopterMenuHandler.adopterMainMenu();
                    break;
                case 3:
                    petMenuHandler.petMainMenu();
                    break;

            }
        }catch (InputException ex) {
            ex.getMessage();
        }
    }

}

