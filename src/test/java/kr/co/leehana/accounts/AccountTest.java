package kr.co.leehana.accounts;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

/**
 * Created by Hana Lee on 2015-10-12 오후 12:04
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-12 오후 12:04
 */
public class AccountTest {

	@Test
	public void getterSetterTest() {
		Account account = new Account();
		account.setUsername("hanalee");
		account.setPassword("password");

		assertThat(account.getUsername(), is("hanalee"));
	}
}