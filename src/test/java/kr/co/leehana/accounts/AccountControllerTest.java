package kr.co.leehana.accounts;

import com.fasterxml.jackson.databind.ObjectMapper;
import kr.co.leehana.App;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Hana Lee on 2015-10-13 오후 3:52
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-13 오후 3:52
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = App.class)
@WebAppConfiguration
@Transactional
public class AccountControllerTest {

	@Autowired
	private WebApplicationContext webApplicationContext;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private AccountService accountService;

	private MockMvc mockMvc;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
	}

	@Test
	public void createAccount() throws Exception {
		AccountDto.Create createDto = new AccountDto.Create();
		createDto.setUsername("voyaging");
		createDto.setPassword("password");

		ResultActions resultActions = mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));
		resultActions.andDo(print());
		resultActions.andExpect(status().isCreated());
		//{"id":1,"username":"voyaging","email":null,"fullName":null,"joined":1444821003172,"updated":1444821003172}
		resultActions.andExpect(jsonPath("$.username", is("voyaging")));

		resultActions = mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON).content(objectMapper
				.writeValueAsString(createDto)));
		resultActions.andDo(print());
		resultActions.andExpect(status().isBadRequest());
		//{"message":"[voyaging] 중복된 username 입니다.","errorCode":"duplicated.username.exception","errors":null}
		resultActions.andExpect(jsonPath("$.errorCode", is("duplicated.username.exception")));
	}

	@Test
	public void createAccount_BadRequest() throws Exception {
		AccountDto.Create createDto = new AccountDto.Create();
		createDto.setUsername(" ");
		createDto.setPassword("1234");

		ResultActions resultActions = mockMvc.perform(post("/accounts").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(createDto)));
		resultActions.andDo(print());
		resultActions.andExpect(status().isBadRequest());
	}

	@Test
	public void getAccounts() throws Exception {
		AccountDto.Create createDto = new AccountDto.Create();
		createDto.setUsername("voyaging");
		createDto.setPassword("password");

		accountService.createAccount(createDto);

		ResultActions resultGetActions = mockMvc.perform(get("/accounts"));

		// {"content":[{"id":1,"username":"voyaging","fullName":null,"email":null,"joined":1444836037938,
		// "updated":1444836037938}],"totalElements":1,"totalPages":1,"last":true,"number":0,"size":20,"sort":null,
		// "numberOfElements":1,"first":true}
		resultGetActions.andDo(print());
		resultGetActions.andExpect(status().isOk());
	}
}
