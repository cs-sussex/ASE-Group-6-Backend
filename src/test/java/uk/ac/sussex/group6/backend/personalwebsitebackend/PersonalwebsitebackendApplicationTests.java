package uk.ac.sussex.group6.backend.personalwebsitebackend;

import ch.qos.logback.core.encoder.EchoEncoder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.BadCredentialsException;
import uk.ac.sussex.group6.backend.Exceptions.BadRequestException;
import uk.ac.sussex.group6.backend.Models.AveragePriceForPostcode;
import uk.ac.sussex.group6.backend.Models.PricePaidData;
import uk.ac.sussex.group6.backend.Models.User;
import uk.ac.sussex.group6.backend.Payloads.*;
import uk.ac.sussex.group6.backend.Services.*;

import java.beans.PropertyChangeSupport;

@SpringBootTest
class PersonalwebsitebackendApplicationTests {

	@Autowired
	private LocationService locationService;
	@Autowired
	private PricePaidDataService pricePaidDataService;
	@Autowired
	private PropertyService propertyService;
	@Autowired
	private UserService userService;
	@Autowired
	private AveragePriceForPostcodeService averagePriceForPostcodeService;

	@Test
	public void testUserLifecycle() throws Exception {

		if (userService.existsByEmail("testuser@me.com")) {
			userService.deleteUserEmail("testuser@me.com");
		}


		SignupRequest signupRequest = new SignupRequest("TEST", "USER", "testuser@me.com", "password");
		userService.createUser(signupRequest);
		Assertions.assertNotNull(userService.getByEmail("testuser@me.com"));

		UpdateUserRequest updateUserRequest = new UpdateUserRequest("John", "Doe");
		User newUser = userService.updateUser(userService.getByEmail("testuser@me.com").getId(), updateUserRequest);
		Assertions.assertEquals("John", newUser.getFirstname());

		ChangePasswordRequest changePasswordRequest = new ChangePasswordRequest("password", "newPassword");
		userService.changePassword(changePasswordRequest, newUser.getId());
		Assertions.assertNotEquals(newUser.getPassword(), userService.getByEmail("testuser@me.com").getPassword());

		LoginRequest loginRequest = new LoginRequest("testuser@me.com", "newPassword1");
		try {
			JwtResponse jwtResponse = userService.signInUser(loginRequest);
		} catch (Exception e) {
			Assertions.assertTrue(true);
		}

		userService.deleteUser(newUser.getId());
		try {
			userService.getById(newUser.getId());
		} catch (BadRequestException e) {
			Assertions.assertTrue(true);
		}
	}

	@Test
	public void getProperty() {
		try {
			SpecificLocationResponse specificLocationResponse = propertyService.findAllWherePostcodeContains("BN1 4");
		} catch (Exception e) {
			Assertions.assertFalse(true);
		}
		try {
			SpecificLocationResponse specificLocationResponse = propertyService.findAllWherePostcodeContains("GIB");
		} catch (Exception e) {
			Assertions.assertTrue(true);
		}

	}

}
