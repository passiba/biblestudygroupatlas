/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.DomainObject;
import fi.passiba.hibernate.Identifiable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.search.annotations.DateBridge;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Resolution;

/**
 *
 * @author jaanakayttajatili
 */
@Entity
@Table(name = "biblesession")
//@AttributeOverride(name = "id", column = @Column(name = "biblesessionid"))
@Indexed
public class Biblesession implements DomainObject,Identifiable {

    private static final long serialVersionUID = 1L;
    private Long id;

    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "biblesessionid")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
  
    private Date sessiontime;

    //private Set<Chapter> chapters = new HashSet<Chapter>(0);
    @DateBridge( resolution = Resolution.DAY )
    @Field(index = Index.TOKENIZED)
    @Temporal(value = TemporalType.TIMESTAMP)
    @Column(name = "sessiontime", nullable = false)
    public Date getSessiontime() {
        return sessiontime;
    }

    public void setSessiontime(Date sessiontime) {
        this.sessiontime = sessiontime;
    }
   /* @IndexedEmbedded
    @OneToMany(cascade = {CascadeType.MERGE,CascadeType.PERSIST}, fetch = FetchType.LAZY, mappedBy = "session")
    public Set<Chapter> getChapters() {
        return chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }*/


    

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
