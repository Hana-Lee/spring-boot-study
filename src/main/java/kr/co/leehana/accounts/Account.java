package kr.co.leehana.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

	@Column(unique = true)
	private String username;

	@JsonIgnore
	private String password;
	private String email;
	private String fullName;

	@Temporal(TemporalType.TIMESTAMP)
	private Date joined;

	@Temporal(TemporalType.TIMESTAMP)
	private Date updated;

	private boolean admin;
}
