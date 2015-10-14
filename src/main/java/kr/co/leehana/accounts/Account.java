package kr.co.leehana.accounts;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CollectionId;

import javax.persistence.Column;
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
}
