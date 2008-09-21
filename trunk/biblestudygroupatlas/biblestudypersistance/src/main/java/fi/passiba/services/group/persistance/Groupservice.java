package fi.passiba.services.group.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.services.persistance.Status;
import java.util.Date;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Groupservice entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "groupservice")
@AttributeOverride( name="id", column = @Column(name="groupservice_id") )
public class Groupservice  extends AuditableEntity {

    // Fields
    
    private Status status;
    private String servicename;
    
   

    @OneToOne(cascade = {}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id")
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "servicename", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getServicename() {
        return this.servicename;
    }

    public void setServicename(String servicename) {
        this.servicename = servicename;
    }

  
}
