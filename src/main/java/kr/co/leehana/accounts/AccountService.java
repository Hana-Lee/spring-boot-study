package kr.co.leehana.accounts;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by Hana Lee on 2015-10-13 오후 3:16
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-13 오후 3:16
 */
@Service
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;
}