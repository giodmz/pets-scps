package com.pets.repository;

import com.pets.entities.Adopter;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AdopterRepository extends JpaRepository<Adopter, Integer> {

    @Query(value = "SELECT * FROM pet WHERE name ILIKE %:name%", nativeQuery = true)
    List<Adopter> findByNameLike(@Param("name") String name);

    Optional<Adopter> findByEmail(String email);


}
