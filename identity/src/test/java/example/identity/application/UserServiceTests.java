package example.identity.application;


import example.common.application.JwtTokenUtil;
import example.common.infrastructure.AppUser;
import example.identity.infrastructure.UserRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;


@SpringBootTest
@AutoConfigureMockMvc
public class UserServiceTests {
    private UserService userService;
    private UserRepository userRepository;
    private JwtTokenUtil jwtTokenUtil;
    private AppUser user;

    @BeforeEach
    void setUp() {
        userRepository = mock(UserRepository.class);
        jwtTokenUtil = mock(JwtTokenUtil.class);
        userService = new UserService(userRepository, jwtTokenUtil);

        user = new AppUser();
        user.setUserUUID("1234");
        user.setUserName("testUser");
        user.setPassword("testPassword");  // Normally this would be hashed
        user.setEmail("testuser@example.com");
        user.setFirstName("Test");
        user.setSurname("User");
    }

    @Test
    @DisplayName("When valid credentials are provided, then a JWT token is generated")
    void whenValidCredentialsProvided_thenGenerateJwtToken() {
        String expectedToken = "valid-jwt-token";


        when(userRepository.findUserByUsernameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.ofNullable(user));  // Mock user found in the repository

        when(jwtTokenUtil.generateToken(user)).thenReturn(expectedToken);

        // Call the service method
        Optional<String> token = userService.authenticate(user.getUserName(), user.getPassword());

        // Assert the JWT token is returned
        assertEquals(Optional.of(expectedToken), token);

        // Verify interactions with repository and JWT utility
        verify(userRepository, times(1)).findUserByUsernameAndPassword(user.getUserName(), user.getPassword());
        verify(jwtTokenUtil, times(1)).generateToken(user);
    }

    @Test
    @DisplayName("When invalid credentials are provided, then no JWT token is generated")
    void whenInvalidCredentialsProvided_thenDoNotGenerateJwtToken() {
        // Mock repository to return empty for invalid credentials
        String invalidUsername = "invalidUser";
        String invalidPassword = "invalidPassword";

        when(userRepository.findUserByUsernameAndPassword(invalidUsername, invalidPassword))
                .thenReturn(Optional.empty());  // No user found in the repository

        // Call the service method
        Optional<String> token = userService.authenticate(invalidUsername, invalidPassword);

        // Assert that no token is returned (empty Optional)
        assertFalse(token.isPresent());

        // Verify interactions with repository
        verify(userRepository, times(1)).findUserByUsernameAndPassword(invalidUsername, invalidPassword);
        verify(jwtTokenUtil, times(0)).generateToken(user);  // JWT token should not be generated
    }

    @Test
    @DisplayName("When repository returns empty, then handle gracefully")
    void whenRepositoryReturnsEmpty_thenHandleGracefully() {
        // Mock repository to return empty (no user found)
        when(userRepository.findUserByUsernameAndPassword(anyString(), anyString()))
                .thenReturn(Optional.empty());

        // Call the service method with any credentials
        Optional<String> token = userService.authenticate("someUser", "somePassword");

        // Assert that no token is generated
        assertFalse(token.isPresent());

        // Verify that JWT token generation did not happen
        verify(jwtTokenUtil, times(0)).generateToken(user);
    }
}
