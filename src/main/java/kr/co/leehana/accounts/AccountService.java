package kr.co.leehana.accounts;

import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
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
@Slf4j
public class AccountService {

//	private Logger log = LoggerFactory.getLogger(getClass());

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	@Autowired
	private PasswordEncoder passwordEncoder;

	public Account createAccount(AccountDto.Create dto) {
		Account account = modelMapper.map(dto, Account.class);
		String username = dto.getUsername();
		if (accountRepository.findByUsername(username) != null) {
			log.error("user duplicated exception. {}", username);
			throw new UserDuplicatedException(username);
		}

		account.setPassword(passwordEncoder.encode(account.getPassword()));

		final Date now = new Date();
		account.setJoined(now);
		account.setUpdated(now);

		return accountRepository.save(account);
	}

	public Account updateAccount(Long id, AccountDto.Update updateDto) {
		Account account = getAccount(id);
		account.setPassword(passwordEncoder.encode(account.getPassword()));
		account.setFullName(updateDto.getFullName());

		return accountRepository.save(account);
	}

	public Account getAccount(Long id) {
		Account account = accountRepository.findOne(id);
		if (account == null) {
			throw new AccountNotFoundException(id);
		}

		return account;
	}

	public void deleteAccount(Long id) {
		accountRepository.delete(getAccount(id));
	}
}
