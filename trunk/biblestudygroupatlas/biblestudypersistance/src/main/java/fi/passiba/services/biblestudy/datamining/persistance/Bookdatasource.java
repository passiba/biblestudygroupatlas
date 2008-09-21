package fi.passiba.services.biblestudy.datamining.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.persistance.Status;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Book entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bookdatasource")
@AttributeOverride(name = "id", column = @Column(name = "bookdatasource_id"))
public class Bookdatasource extends AuditableEntity {

    // Fields
    private String weburlName;
    private Book book;
    private Status status;

    // Constructors
    /** default constructor */
    public Bookdatasource() {
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_status_id", referencedColumnName = "status_id")
    public Status getStatus() {
        return this.status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Column(name = "weburlname", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getWeburlName() {
        return weburlName;
    }

    public void setWeburlName(String weburlName) {
        this.weburlName = weburlName;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bookdatasource other = (Bookdatasource) obj;

        if (this.getId() != other.getId() && (this.getId() == null || !this.getId().equals(other.getId()))) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 19 * hash + (this.getId() != null ? this.getId().hashCode() : 0);
        return hash;
    }
}