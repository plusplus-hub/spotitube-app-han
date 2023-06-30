package org.han.dea.spotitube.nigel.service;

import org.han.dea.spotitube.nigel.model.TokenDTO;
import org.han.dea.spotitube.nigel.model.UserDTO;
import org.han.dea.spotitube.nigel.persistence.dao.ITokenDAO;
import org.junit.jupiter.api.*;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class TokenServiceTest {

    @Mock
    private ITokenDAO tokenDAO;

    private TokenService tokenService;
    private TokenDTO testToken;
    private UserDTO testUser;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        tokenService = new TokenService(tokenDAO);

        testUser = new UserDTO();
        testUser.setUser("testUser");
        testUser.setPassword("testPassword");
        testUser.setId(1);

        testToken = new TokenDTO();
        testToken.setUser(testUser.getUser());
        testToken.setToken("ABCD-EFGH-IJKL");
    }

    @Test
    void testGetToken_ValidToken_ReturnsToken() {
        when(tokenDAO.getToken(any())).thenReturn(testToken);
        TokenDTO result = tokenService.getToken(testToken);
        assertNotNull(result);
        assertEquals(testToken, result);
    }

    @Test
    void testSaveToken_ValidToken_ReturnsTrue() {
        when(tokenDAO.saveToken(any())).thenReturn(true);
        boolean result = tokenService.saveToken(testToken);
        assertTrue(result);
    }

    @Test
    void testSaveToken_InvalidToken_ReturnsFalse() {
        when(tokenDAO.saveToken(any())).thenReturn(false);
        boolean result = tokenService.saveToken(testToken);
        assertFalse(result);
    }

    @Test
    void testCreateToken_ValidUser_ReturnsToken() {
        when(tokenDAO.saveToken(any())).thenReturn(true);
        TokenDTO result = tokenService.createToken(testUser);
        assertNotNull(result);
        assertNotNull(result.getToken());
        assertEquals(testUser.getUser(), result.getUser());
    }
}
