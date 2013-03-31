package poke.server.storage.jpa;


import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the userinfo database table.
 * 
 */
@Entity
@Table(name="userinfo")
@NamedQueries({@NamedQuery(name = "getPassword", query = "SELECT password from Userinfo password where password.email =:id"),
@NamedQuery(name = "getUserid", query = "SELECT userid from Userinfo userid where userid.email =:id"),
@NamedQuery(name = "getFirstname", query = "SELECT firstname from Userinfo firstname where firstname.email =:id"),
@NamedQuery(name = "getLastname", query = "SELECT lastname from Userinfo lastname where lastname.email =:id")})

public class Userinfo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Integer userid;
	
	@Column
	private String email;

	@Column
	private String firstname;

	@Column
	private String lastname;

	@Column
	private String password;
	
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	

	// bi-directional many-to-one association to Image
	//@OneToMany(mappedBy = "id", cascade = { CascadeType.ALL })
	//private int userid;

	public Userinfo() {
		
	}

	public Userinfo(String email, String firstname, String lastname,String password) {
		
		this.firstname = firstname;
		this.lastname = lastname;
		this.password = password;
		this.email=email;
		

	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getFirstname() {
		return this.firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return this.lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getPassword() {
		return this.password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	

}