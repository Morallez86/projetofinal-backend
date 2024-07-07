package aor.paj.projetofinalbackend.bean;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.NoResultException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import aor.paj.projetofinalbackend.bean.TokenBean;
import aor.paj.projetofinalbackend.dao.TokenDao;
import aor.paj.projetofinalbackend.dao.UserDao;
import aor.paj.projetofinalbackend.entity.TokenEntity;
import aor.paj.projetofinalbackend.entity.UserEntity;


public class TokenBeanTest {

    @Mock
    private TokenDao tokenDaoMock;

    @Mock
    private UserDao userDaoMock;

    @InjectMocks
    private TokenBean tokenBean;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testFindTokenByValue() {
        // Mocking behavior
        TokenEntity mockToken = new TokenEntity();
        when(tokenDaoMock.findTokenByValue("validToken")).thenReturn(mockToken);

        // Test
        TokenEntity foundToken = tokenBean.findTokenByValue("validToken");
        assertEquals(mockToken, foundToken);
    }

    @Test
    public void testFindUserByToken() {
        // Mocking behavior
        UserEntity mockUser = new UserEntity();
        when(tokenDaoMock.findUserByTokenValue("validToken")).thenReturn(mockUser);

        // Test
        UserEntity foundUser = tokenBean.findUserByToken("validToken");
        assertEquals(mockUser, foundUser);
    }

    @Test
    public void testIsTokenActive() {
        // Mocking behavior
        TokenEntity activeToken = new TokenEntity();
        activeToken.setActiveToken(true);
        when(tokenDaoMock.findTokenByValue("activeToken")).thenReturn(activeToken);
        when(tokenDaoMock.findTokenByValue("inactiveToken")).thenThrow(new NoResultException());

        // Test
        assertTrue(tokenBean.isTokenActive("activeToken"));
        assertFalse(tokenBean.isTokenActive("inactiveToken"));
    }

    @Test
    public void testDeactivateToken() {
        // Mocking behavior
        TokenEntity activeToken = new TokenEntity();
        activeToken.setActiveToken(true);
        when(tokenDaoMock.findTokenByValue("activeToken")).thenReturn(activeToken);

        // Stubbing for void method
        doNothing().when(tokenDaoMock).merge(any(TokenEntity.class));

        // Test
        assertTrue(tokenBean.deactivateToken("activeToken"));
        assertFalse(activeToken.isActiveToken()); // Verify token was deactivated
    }

    @Test
    public void testRemoveExpiredTokens() {
        // Mocking behavior
        LocalDateTime now = LocalDateTime.now();
        List<TokenEntity> expiredTokens = new ArrayList<>();
        TokenEntity expiredToken1 = new TokenEntity();
        expiredToken1.setExpirationTime(now.minusDays(1)); // Expired token
        expiredTokens.add(expiredToken1);
        when(tokenDaoMock.findExpiredTokens(any(LocalDateTime.class))).thenReturn(expiredTokens);

        // Stubbing for void method
        doNothing().when(tokenDaoMock).merge(any(TokenEntity.class));

        // Test
        tokenBean.removeExpiredTokens();
        assertFalse(expiredToken1.isActiveToken()); // Verify expired token was deactivated
    }
}
