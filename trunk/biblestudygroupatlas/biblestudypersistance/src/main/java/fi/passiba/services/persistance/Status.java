package fi.passiba.services.persistance;


import fi.passiba.hibernate.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * Status entity.
 * 
 * @author haverinen
 */
@Entity
@Table(name = "status")
@AttributeOverride( name="id", column = @Column(name="status_id") )
public class Status  extends  BaseEntity  {

	// Fields
	private String statusname;
	
	
	
	

	@Column(name = "statusname", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
	public String getStatusname() {
		return this.statusname;
	}

	public void setStatusname(String statusname) {
		this.statusname = statusname;
	}

	

}