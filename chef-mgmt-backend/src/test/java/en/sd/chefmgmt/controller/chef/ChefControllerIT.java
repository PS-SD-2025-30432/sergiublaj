package en.sd.chefmgmt.controller.chef;

import com.fasterxml.jackson.databind.ObjectMapper;
import en.sd.chefmgmt.controller.ControllerTestData;
import en.sd.chefmgmt.model.dto.chef.ChefRequestDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestPropertySource("classpath:application-test.yml")
class ChefControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private static final UUID EXISTING_CHEF_ID = UUID.fromString("11111111-1111-1111-1111-111111111111");

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenAdmin_whenGetAllChefs_thenReturn200AndList() throws Exception {
        // when + then
        mockMvc.perform(get("/v1/chefs"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.elements", hasSize(greaterThanOrEqualTo(1))));
    }

    @Test
    void givenNoAuth_whenGetAllChefs_thenReturn403() throws Exception {
        // when + then
        mockMvc.perform(get("/v1/chefs"))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenValidId_whenGetChefById_thenReturn200() throws Exception {
        // when + then
        mockMvc.perform(get("/v1/chefs/" + EXISTING_CHEF_ID))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EXISTING_CHEF_ID.toString()))
                .andExpect(jsonPath("$.name").value("John Doe"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenInvalidId_whenGetChefById_thenReturn404() throws Exception {
        // given
        UUID invalidId = UUID.randomUUID();

        // when + then
        mockMvc.perform(get("/v1/chefs/" + invalidId))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenValidRequest_whenSaveChef_thenReturn201() throws Exception {
        // given
        ChefRequestDTO dto = ControllerTestData.validChefRequestDto();

        // when + then
        mockMvc.perform(post("/v1/chefs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("New Chef"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenInvalidRequest_whenSaveChef_thenReturn400() throws Exception {
        // given
        ChefRequestDTO invalidDto = ControllerTestData.invalidChefRequestDto();

        // when + then
        mockMvc.perform(post("/v1/chefs")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenAdmin_whenDeleteChef_thenReturn204() throws Exception {
        // when + then
        mockMvc.perform(delete("/v1/chefs/" + EXISTING_CHEF_ID))
                .andExpect(status().isNoContent());
    }

    @Test
    void givenNoAuth_whenDeleteChef_thenReturn403() throws Exception {
        // when + then
        mockMvc.perform(delete("/v1/chefs/" + EXISTING_CHEF_ID))
                .andExpect(status().isForbidden());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void givenInvalidId_whenDeleteChef_thenReturn404() throws Exception {
        // given
        UUID invalidId = UUID.randomUUID();

        // when + then
        mockMvc.perform(delete("/v1/chefs/" + invalidId))
                .andExpect(status().isNotFound());
    }
}