package kr.co.leehana.accounts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Hana Lee on 2015-10-13 오후 3:12
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-13 오후 3:12
 */
@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {
	Account findByUsername(String username);
}
