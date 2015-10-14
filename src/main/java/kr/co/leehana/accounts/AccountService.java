package kr.co.leehana.accounts;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Created by Hana Lee on 2015-10-13 오후 3:16
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-13 오후 3:16
 */
@Service
@Transactional
public class AccountService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	public Account createAccount(AccountDto.Create dto) {
		Account account = modelMapper.map(dto, Account.class);
		String username = dto.getUsername();
		if (accountRepository.findByUsername(username) != null) {
			throw new UserDuplicatedException(username);
		}

		final Date now = new Date();
		account.setJoined(now);
		account.setUpdated(now);

		return accountRepository.save(account);
	}
}
