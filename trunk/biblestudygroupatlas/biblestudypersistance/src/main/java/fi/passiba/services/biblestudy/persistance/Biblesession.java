/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.AuditableEntity;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.OneToMany;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

/**
 *
 * @author jaanakayttajatili
 */
@Entity
@Table(name = "biblesession")
@AttributeOverride(name = "id", column = @Column(name = "biblesessionid"))
@Searchable
public class Biblesession extends AuditableEntity {

    private static final long serialVersionUID = 1L;

    @SearchableProperty(name="sessiontime")
    @SearchableMetaData(name = "biblesessiontime")
    private Date sessiontime;

    @SearchableReference
    private Set<Chapter> chapters = new HashSet<Chapter>(0);

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "sessiontime", nullable = false)
    public Date getSessiontime() {
        return sessiontime;
    }

    public void setSessiontime(Date sessiontime) {
        this.sessiontime = sessiontime;
    }
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "session")
    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }


    

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Biblesession other = (Biblesession) obj;

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
