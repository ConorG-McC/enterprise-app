package example.identity.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import example.identity.application.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import java.util.Optional;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class IdentityControllerTests {

    private static final String API_BASE_URL = "/validate";
    private static final String VALID_JWT_TOKEN = "valid-jwt-token";
    private static final String VALID_USERNAME = "validUser";
    private static final String VALID_PASSWORD = "validPassword";
    private static final String INVALID_USERNAME = "invalidUser";
    private static final String INVALID_PASSWORD = "invalidPassword";

    private MockMvc mockMvc;
    private UserService userService;
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        userService = mock(UserService.class);
        IdentityController identityController = new IdentityController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(identityController).build();

        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new Jdk8Module());
        objectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    @Test
    @DisplayName("When valid credentials are provided, then return JWT token")
    void whenValidCredentialsProvided_thenReturnJwtToken() throws Exception {
        when(userService.authenticate(VALID_USERNAME, VALID_PASSWORD))
                .thenReturn(Optional.of(VALID_JWT_TOKEN));

        UserDetailsRequest request = new UserDetailsRequest();
        request.setUsername(VALID_USERNAME);
        request.setPassword(VALID_PASSWORD);
        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().string(VALID_JWT_TOKEN));
    }

    @Test
    @DisplayName("When invalid credentials are provided, then return bad request")
    void whenInvalidCredentialsProvided_thenReturnBadRequest() throws Exception {
        when(userService.authenticate(INVALID_USERNAME, INVALID_PASSWORD))
                .thenReturn(Optional.empty());

        UserDetailsRequest request = new UserDetailsRequest();
        request.setUsername(VALID_USERNAME);
        request.setPassword(VALID_PASSWORD);        String requestBody = objectMapper.writeValueAsString(request);

        mockMvc.perform(post(API_BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid details provided"));
    }
    @Test
    @DisplayName("When missing username or password, return bad request")
    void whenMissingFieldsInRequest_thenReturnBadRequest() throws Exception {
        String missingPasswordRequest = "{\"username\":\"validUser\"}";

        mockMvc.perform(post("/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(missingPasswordRequest))
                .andExpect(status().isBadRequest());

        String missingUsernameRequest = "{\"password\":\"validPassword\"}";

        mockMvc.perform(post("/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(missingUsernameRequest))
                .andExpect(status().isBadRequest());
    }

    @Test
    @DisplayName("When malformed JSON is sent, return bad request")
    void whenMalformedJsonProvided_thenReturnBadRequest() throws Exception {
        String malformedJson = "{username:\"validUser\", password:\"validPassword\""; // missing closing brace

        mockMvc.perform(post("/validate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(malformedJson))
                .andExpect(status().isBadRequest());
    }


}
