package services;

import com.pets.entities.Adopter;
import com.pets.entities.Pet;
import com.pets.exceptions.ObjectNotFoundException;
import com.pets.repository.AdopterRepository;
import com.pets.repository.PetRepository;
import com.pets.services.AdopterService;
import com.pets.services.PetService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AdopterServiceTest {
    @Mock
    private AdopterRepository rep;

    @InjectMocks
    private AdopterService service;

    @Test
    void findByIdReturnPetWhenIdIsValid() {
        Adopter adopter = Adopter.builder().id(1).name("Geraldo Costa").build();
        when(rep.findById(1)).thenReturn(Optional.of(adopter));

        Adopter resultado = service.findById(1);

        assertThat(resultado.getName()).isEqualTo("Geraldo Costa");
    }

    @Test
    void findByIdThrowsExceptionWhenIdIsNull() {
        assertThrows(IllegalArgumentException.class, () -> service.findById(null));
    }

    @Test
    void findByIdThrowsExceptionWhenIdIsNotFound() {
        when(rep.findById(99)).thenReturn(Optional.empty());

        assertThrows(ObjectNotFoundException.class, () -> service.findById(99));
    }
}
