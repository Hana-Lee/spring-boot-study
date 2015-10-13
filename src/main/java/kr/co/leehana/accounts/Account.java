package kr.co.leehana.accounts;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

/**
 * Created by Hana Lee on 2015-10-12 오전 11:23
 *
 * @author {@link "mailto:i@leehana.co.kr" "Hana Lee"}
 * @since 2015-10-12 오전 11:23
 */
@Entity
@Getter
@Setter
public class Account {

	@Id
	@GeneratedValue
	private long id;

	private String username;
	private String password;
	private String email;
	private String fullName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date joined;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;
}
