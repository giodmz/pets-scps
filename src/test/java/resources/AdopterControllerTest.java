package resources;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pets.PetsApp;
import com.pets.controllers.AdopterController;
import com.pets.dto.AdopterDTO;
import com.pets.entities.Adopter;
import com.pets.services.AdopterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = AdopterController.class)
@ContextConfiguration(classes = PetsApp.class)
public class AdopterControllerTest {

    @MockitoBean
    private AdopterService service;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private MockMvc mockMvc;

    @Test
    void shouldReturnPetsByName() throws Exception {
        Adopter adopter = new Adopter();
        adopter.setName("Sylvanas");

        when(service.findByNameLike("Sylvanas"))
                .thenReturn(List.of(adopter));

        List<AdopterDTO> expected = List.of(new AdopterDTO(adopter));
        String expectedJson = objectMapper.writeValueAsString(expected);

        mockMvc.perform(get("/adopters/search")
                        .param("name", "Sylvanas"))
                .andExpect(status().isOk())
                .andExpect(content().json(expectedJson));
    }
}
