import com.pets.entities.Pet;
import com.pets.exceptions.ObjectNotFoundException;
import com.pets.repository.PetRepository;
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
class PetServiceTest {

    @Mock
    private PetRepository rep;

    @InjectMocks
    private PetService service;

    @Test
    void findByIdReturnPetWhenIdIsValid() {
        Pet pet = Pet.builder().id(1).name("Astarion").build();
        when(rep.findById(1)).thenReturn(Optional.of(pet));

        Pet resultado = service.findById(1);

        assertThat(resultado.getName()).isEqualTo("Astarion");
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