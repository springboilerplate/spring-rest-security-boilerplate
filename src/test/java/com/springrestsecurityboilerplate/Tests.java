package com.springrestsecurityboilerplate;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.nio.charset.Charset;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.http.MediaType;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springrestsecurityboilerplate.user.AppUser;
import com.springrestsecurityboilerplate.user.UserRepository;
import com.springrestsecurityboilerplate.user.UserService;

import cucumber.api.java.en.And;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import io.jsonwebtoken.lang.Assert;

@SpringBootTest
// @WebAppConfiguration
// @AutoConfigureMockMvc
@ContextConfiguration
public class Tests {

	private static MockMvc mockMvc;

	@Autowired
	WebApplicationContext wac;

	@Autowired
	UserService userService;

	@Autowired
	UserRepository userRepository;

	@Autowired
	private ObjectMapper objectMapper;

	private static JacksonTester<AppUser> jsonTester;

	AppUser user = new AppUser();

	AppUser loginUser = new AppUser();

	AppUser tempUser;

	static String bearerToken;

	String lastToken;

	@Autowired
	private FilterChainProxy springSecurityFilterChain;

	public static final MediaType APPLICATION_JSON_UTF8 = new MediaType(MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	// @BeforeClass
	// public void setup() {
	// System.out.println("Before executed");
	//// this.mockMvc =
	// MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
	//// JacksonTester.initFields(this, objectMapper);
	//
	// }

	@Given("^configurations /config$")
	public void configg() throws Throwable {

		mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).addFilter(springSecurityFilterChain).build();
		JacksonTester.initFields(this, objectMapper);
		System.out.println("setup executed");

	}

	@When("^username password and email are \"([^\"]*)\" AND \"(.*?)\" AND \"(.*?)\"$")
	public void userRegister(String username, String password, String email) throws Throwable {

		user.setUsername(username);
		user.setPassword(password);
		user.setEmail(email);

	}

	@Then("^visitor calls /register$")
	public void performRegister() throws Throwable {

		final String personDTOJson = jsonTester.write(user).getJson();

		mockMvc.perform(post("/register").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).
				// andExpect(jsonPath("$.username",is("Destan"))).
				andDo(MockMvcResultHandlers.print());

	}

	@When("^Resend Token with unregistered user \"([^\"]*)\"$")
	public void resendUnregisteredUserToken(String email) throws Throwable {
		MvcResult mvcResult;

		tempUser = new AppUser();

		mvcResult = mockMvc.perform(get("/resend/{email}", email)).andExpect(status().isOk()).andReturn();

		tempUser = userRepository.findByEmail(email);

		// Assert.isNull(tempUser, "Should be Null");

	}

	@Then("^Make sure that user is null$")
	public void userIsNull() throws Throwable {

		Assert.isNull(tempUser, "Should be Null");

	}

	@When("^Resend Token \"([^\"]*)\"$")
	public void resendToken(String email) throws Throwable {
		MvcResult mvcResult;

		tempUser = new AppUser();
		mvcResult = mockMvc.perform(get("/resend/{email}", email)).andExpect(status().isOk()).andReturn();
		tempUser = userRepository.findByEmail(email);

		lastToken = tempUser.getToken().getToken();
		System.out.println("NEW TOKEN IS " + lastToken);

	}

	@And("^Make sure that user is not null$")
	public void userIsNotNull() throws Throwable {

		Assert.notNull(tempUser, "Should not be Null");
	}

	@Then("^Make sure that user is not active$")
	public void userIsNotActive() throws Throwable {

		assertEquals(tempUser.getIsActive(), false);
	}

	@When("^Confirm Token$")
	public void confirmToken() throws Throwable {
		MvcResult mvcResult;

		mvcResult = mockMvc.perform(get("/confirm/{email}", lastToken)).andExpect(status().isOk()).andReturn();
	}

	@Then("^Make sure that user is active \"([^\"]*)\"$")
	public void userIsActive(String email) throws Throwable {

		tempUser = userRepository.findByEmail(email);
		assertEquals(tempUser.getIsActive(), true);
	}

	@When("^Resend Token for already confirmed user \"([^\"]*)\"$")
	public void resendForAlreadyConfirmed(String email) throws Throwable {
		MvcResult mvcResult;

		tempUser = new AppUser();

		mvcResult = mockMvc.perform(get("/resend/{email}", email)).andExpect(status().isOk()).andReturn();

		tempUser = userRepository.findByEmail(email);

		// Assert.notNull(tempUser, "should not be null");
		// assertEquals(tempUser.getIsActive(), true);

	}

	@When("^Successful login username and password are \"([^\"]*)\" AND \"(.*?)\"$")
	public void userLogin(String username, String password) throws Throwable {
		MvcResult mvcResult;
		loginUser.setUsername(username);
		loginUser.setPassword(password);
		final String personDTOJson = jsonTester.write(loginUser).getJson();

		mvcResult = mockMvc.perform(post("/login").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().isOk()).
				// andExpect(jsonPath("$.username",is("Destan"))).
				andDo(MockMvcResultHandlers.print()).andReturn();

		bearerToken = mvcResult.getResponse().getHeader("Authorization");

		System.out.println("Value is =" + bearerToken);

	}

	@When("^Login with bad creds username and password are \"([^\"]*)\" AND \"(.*?)\"$")
	public void userLoginWithBadCreds(String username, String password) throws Throwable {

		loginUser.setUsername(username);
		loginUser.setPassword(password);
		final String personDTOJson = jsonTester.write(loginUser).getJson();

		mockMvc.perform(post("/login").content(personDTOJson).contentType(APPLICATION_JSON_UTF8))
				.andExpect(status().is4xxClientError()).
				// andExpect(jsonPath("$.username",is("Destan"))).
				andDo(MockMvcResultHandlers.print());

	}

	@Then("^Access with token /test$") // authentication
	public void displayBearerToken() throws Exception {
		mockMvc.perform(get("/test").header("authorization", bearerToken)).andExpect(status().isOk());
	}

	@Then("^Access without token /test$")
	public void the_client_issues_POST_hello2() throws Throwable {

		mockMvc.perform(get("/test")).andExpect(status().is4xxClientError());
		System.out.println("testauth");

	}

}
