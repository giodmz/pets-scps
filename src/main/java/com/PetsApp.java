package com;

//import com.pets.config.MenuHandler;
import com.pets.config.MenuHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class PetsApp {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(PetsApp.class, args);
        MenuHandler menuHandler = context.getBean(MenuHandler.class);
        menuHandler.initialMenu();
    }

}
