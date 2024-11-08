

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

import org.hibernate.annotations.TimeZoneStorage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import redsocial.user.UserApplication;
import redsocial.user.UserController;
import redsocial.user.UserNotFoundException;
import redsocial.user.UserService;
import static org.mockito.BDDMockito.given;


@WebMvcTest(UserController.class)
@ContextConfiguration(classes = UserApplication.class) // Añade esta línea
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    // @Test
    // public void testGetAllUsers() throws Exception {
    //     mockMvc.perform(get("/users"))
    //             .andExpect(status().isOk())
    //             .andExpect(jsonPath("$", hasSize(2)));
    // }

    // @Test
    // public void testGetUserById() throws Exception {
    //     mockMvc.perform(get("/users/1"))
    //             .andExpect(status().isOk());
    // }

    // @Test 
    // public void testGetUserByIdNotFound() throws Exception {
    //     mockMvc.perform(get("/users/999"))
    //             .andExpect(status().isNotFound());
    // }

    @Test
    void getUserById_UserNotFoundException() throws Exception {
        // en lugar de buscar en una bdd, simulamos el comportamiento que queremos
        given(userService.getUserById(12L))
        .willThrow(new UserNotFoundException(12L));

        // Realizar la petición y verificar la respuesta
        mockMvc.perform(get("/users/12"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.message").value("Usuario no encontrado"));
    }
}
