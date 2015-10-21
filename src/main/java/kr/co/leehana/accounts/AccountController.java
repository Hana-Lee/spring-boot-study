package kr.co.leehana.accounts;

import kr.co.leehana.commons.ErrorResponse;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.web.bind.annotation.RequestMethod.*;

/**
 * Created by Hana Lee on 2015-10-12 오전 11:27
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-12 오전 11:27
 */
@RestController
public class AccountController {

	@Autowired
	private AccountService accountService;

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private ModelMapper modelMapper;

	@RequestMapping(value = "/accounts", method = POST)
	public ResponseEntity createAccount(@RequestBody @Valid AccountDto.Create create, BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			ErrorResponse errorResponse = new ErrorResponse();
			errorResponse.setMessage("잘못된 요청입니다.");
			errorResponse.setErrorCode("bad.request");
			return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
		}

		Account newAccount = accountService.createAccount(create);
		return new ResponseEntity<>(newAccount, HttpStatus.CREATED);
//		return new ResponseEntity<>(modelMapper.map(newAccount, AccountDto.Response.class), HttpStatus.CREATED);
	}

	@RequestMapping(value = "/accounts", method = GET)
	@ResponseStatus(value = HttpStatus.OK)
	public PageImpl<AccountDto.Response> getAccounts(Pageable pageable) {
		Page<Account> pages = accountRepository.findAll(pageable);
		List<AccountDto.Response> content = pages.getContent().parallelStream().map(account -> modelMapper.map
				(account, AccountDto.Response.class)).collect(Collectors.toList());
		return new PageImpl<>(content, pageable, pages.getTotalElements());
	}

	@RequestMapping(value = "/accounts/{id}", method = GET)
	@ResponseStatus(HttpStatus.OK)
	public AccountDto.Response getAccount(@PathVariable Long id) {
		return modelMapper.map(accountService.getAccount(id), AccountDto.Response.class);
	}

	// 전체 업데이트 (전체 필드 업데이트 PUT)
	// 부분 업데이트(username or username, password or username, fullName : PATCH)
	@RequestMapping(value = "/accounts/{id}", method = PUT)
	public ResponseEntity updateAccount(@PathVariable Long id, @RequestBody @Valid AccountDto.Update updateDto,
	                                    BindingResult bindingResult) {
		if (bindingResult.hasErrors()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		Account updatedAccount = accountService.updateAccount(id, updateDto);
		return new ResponseEntity<>(modelMapper.map(updatedAccount, AccountDto.Response.class), HttpStatus.OK);
	}

	@RequestMapping(value = "/accounts/{id}", method = DELETE)
	public ResponseEntity deleteAccount(@PathVariable Long id) {
		accountService.deleteAccount(id);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@ExceptionHandler(UserDuplicatedException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleUserDuplicatedException(UserDuplicatedException ex) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("[" + ex.getUsername() + "] 중복된 username 입니다.");
		errorResponse.setErrorCode("duplicated.username.exception");
		return errorResponse;
	}

	@ExceptionHandler(AccountNotFoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public ErrorResponse handleAccountNotFoundException(AccountNotFoundException e) {
		ErrorResponse errorResponse = new ErrorResponse();
		errorResponse.setMessage("[" + e.getId() + "] 에 해당하는 계정이 없습니다.");
		errorResponse.setErrorCode("account.not.found.exception");
		return errorResponse;
	}
}
