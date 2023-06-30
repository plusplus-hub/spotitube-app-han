package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.exception.UnauthorizedUserException;
import org.han.dea.spotitube.nigel.model.UserDTO;
import org.han.dea.spotitube.nigel.persistence.dao.IUserDAO;
import org.junit.jupiter.api.*;
import org.mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceTest {
    @Mock
    private IUserDAO userDAO;
    private AuthService authService;
    private UserDTO testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        authService = new AuthService(userDAO);

        testUser = new UserDTO();
        testUser.setUser("testUser");
        testUser.setPassword("$2a$10$7.8vu2AjE4aE7W5BoS.9Auk/BB3eHNL9AeCJYKp1e/39NQJbHLH2"); //bcrypt hash for "password"
        testUser.setId(1);
    }

    @Test
    void testValidatePassword_ValidPassword_ReturnsTrue() {
        when(userDAO.getUserByUsername("testUser")).thenReturn(testUser);
        UserDTO inputUser = new UserDTO("testUser", "password", 1);
        assertTrue(authService.validatePassword(inputUser));
    }

    @Test
    void testValidatePassword_InvalidPassword_ThrowsException() {
        when(userDAO.getUserByUsername("testUser")).thenReturn(testUser);
        UserDTO inputUser = new UserDTO("testUser", "wrongPassword", 1);
        assertThrows(UnauthorizedUserException.class, () -> authService.validatePassword(inputUser));
    }

    @Test
    void testValidatePassword_InvalidUser_ThrowsException() {
        when(userDAO.getUserByUsername("testUser")).thenReturn(null);
        UserDTO inputUser = new UserDTO("invalidUser", "password", 1);
        assertThrows(UnauthorizedUserException.class, () -> authService.validatePassword(inputUser));
    }

    @Test
    void testGetUserByToken_ValidToken_ReturnsUser() {
        when(userDAO.getUserIdByToken("validToken")).thenReturn(testUser);
        assertEquals(testUser, authService.getUserByToken("validToken"));
    }

    @Test
    void testGetUserByToken_InvalidToken_ThrowsException() {
        when(userDAO.getUserIdByToken("invalidToken")).thenReturn(null);
        assertThrows(UnauthorizedUserException.class, () -> authService.getUserByToken("invalidToken"));
    }
}
