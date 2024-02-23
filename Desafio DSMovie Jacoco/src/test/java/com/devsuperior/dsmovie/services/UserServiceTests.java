package com.devsuperior.dsmovie.services;

import com.devsuperior.dsmovie.entities.UserEntity;
import com.devsuperior.dsmovie.projections.UserDetailsProjection;
import com.devsuperior.dsmovie.repositories.UserRepository;
import com.devsuperior.dsmovie.tests.UserDetailsFactory;
import com.devsuperior.dsmovie.tests.UserFactory;
import com.devsuperior.dsmovie.utils.CustomUserUtil;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

@ExtendWith(SpringExtension.class)
@ContextConfiguration
public class UserServiceTests {

    @InjectMocks
    private UserService service;

	@Mock
	private UserRepository repository;

	@Mock
    private CustomUserUtil userUtil;
	private UserEntity user;
    private String nonExistingUsername;

    @BeforeEach
    void setUp() throws Exception {
		user = UserFactory.createUserEntity();
        nonExistingUsername = "user@gmail.com";

        List<UserDetailsProjection> projections = UserDetailsFactory.createCustomAdminClientUser(user.getUsername());

		Mockito.when(userUtil.getLoggedUsername()).thenReturn(user.getUsername());
		Mockito.when(repository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));

        Mockito.when(repository.searchUserAndRolesByUsername(user.getUsername())).thenReturn(projections);
    }

    @Test
    public void authenticatedShouldReturnUserEntityWhenUserExists() {
        UserEntity result = service.authenticated();

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getId(), user.getId());
        Assertions.assertEquals(result.getUsername(), user.getUsername());
    }

    @Test
    public void authenticatedShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
        Mockito.doThrow(ClassCastException.class).when(userUtil).getLoggedUsername();

        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.authenticated();
        });
    }

    @Test
    public void loadUserByUsernameShouldReturnUserDetailsWhenUserExists() {
		UserDetails result = service.loadUserByUsername(user.getUsername());

        Assertions.assertNotNull(result);
        Assertions.assertEquals(result.getUsername(), user.getUsername());
    }

    @Test
    public void loadUserByUsernameShouldThrowUsernameNotFoundExceptionWhenUserDoesNotExists() {
        Assertions.assertThrows(UsernameNotFoundException.class, () -> {
            service.loadUserByUsername(nonExistingUsername);
        });
    }
}
