package tse.poc.timemgr.tse;

import com.jayway.jsonpath.JsonPath;


import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.util.NestedServletException;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
class UnitTests {

	@Autowired
	public MockMvc mvc;
	@Autowired
	WebApplicationContext webApplicationContext;

	@Test
	@WithMockUser(username = "TestAdmin", password = "user1234", roles = "ADMIN")
	public void findAllUsers() throws Exception {

		mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
		MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/users/all")
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		String response = mvcResult.getResponse().getContentAsString();
		assertEquals(JsonPath.parse(response).read("$[0].username"),"TestUser");

	}

	@Test
	@WithMockUser(username = "TestUser", password = "user1234", roles = "USER")
	void findAllUsersNoAuth() throws Exception {

		//Not Authorized so should throw exception

		assertThrows(
				NestedServletException.class,
				() -> { mvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
					MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.get("/users/all")
							.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn(); }
		);




	}

}
