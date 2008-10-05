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

/**
 *
 * @author jaanakayttajatili
 */
@Entity
@Table(name = "biblesession")
@AttributeOverride(name = "id", column = @Column(name = "biblesessionid"))
public class Biblesession extends AuditableEntity {

    private static final long serialVersionUID = 1L;
    private Date sessiontime;
    private Set<Verse> verses = new HashSet<Verse>(0);

    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "sessiontime", nullable = false)
    public Date getSessiontime() {
        return sessiontime;
    }

    public void setSessiontime(Date sessiontime) {
        this.sessiontime = sessiontime;
    }


    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "session")
    public Set<Verse> getVerses() {
        return this.verses;
    }

    public void setVerses(Set<Verse> verses) {
        this.verses = verses;
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
