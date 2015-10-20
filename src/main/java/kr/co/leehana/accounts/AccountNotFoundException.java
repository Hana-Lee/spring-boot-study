package kr.co.leehana.accounts;

/**
 * Created by Hana Lee on 2015-10-21 오전 1:12
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-21 오전 1:12
 */
public class AccountNotFoundException extends RuntimeException {

	private Long id;

	public AccountNotFoundException(Long id) {
		this.id = id;
	}

	public Long getId() {
		return id;
	}
}
