package kr.co.leehana.accounts;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by Hana Lee on 2015-10-12 오전 11:27
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-12 오전 11:27
 */
@RestController
public class AccountController {

	@RequestMapping("/hello")
	public String hello() {
		return "Hello world";
	}
}
