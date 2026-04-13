package resources;


import com.pets.PetsApp;
import com.pets.controllers.PetController;
import com.pets.services.PetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = PetController.class)
@ContextConfiguration(classes = PetsApp.class)
public class PetControllerTest {


    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private PetService petService;

    /* o corpo do request http que você vai enviar.
    é exatamente o que um cliente mandaria para a API. */
    @Test
    void insert_Return400When_WeightIsNegative() throws Exception {
        /*
        post("/pets") — métod0 POST para o endpoint /pets
        .contentType(APPLICATION_JSON) — header dizendo que o corpo é JSON
        .content(json) — o corpo do request
        */
        String json = """
            {
                "name": "Astarion",
                "gender": "MALE",
                "age": 5,
                "weight": -10.0,
                "species": "CAT",
                "status": "AVAILABLE"
            }
            """;

        // simula o request
        mockMvc.perform(post("/pets")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isBadRequest());
    }

}
