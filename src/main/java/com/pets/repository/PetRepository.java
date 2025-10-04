package com.pets.repository;

import com.pets.entities.Pet;
import com.pets.enums.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PetRepository extends JpaRepository<Pet, Integer> {

    @Query(value = "SELECT * FROM pet WHERE name ILIKE %:name%", nativeQuery = true)
    List<Pet> findByNameLike(@Param("name") String name);


    @Query("SELECT p FROM Pet p WHERE LOWER(p.name) " +
            "LIKE LOWER(CONCAT('%', :name, '%')) " +
            "AND p.status = com.pets.enums.Status.AVAILABLE")
    List<Pet> findAvailableToAdoptPet(@Param("name") String name);

    @Query("SELECT p FROM Pet p WHERE p.status = com.pets.enums.Status.AVAILABLE")
    List<Pet> listAvailableToAdoptPet();


}
