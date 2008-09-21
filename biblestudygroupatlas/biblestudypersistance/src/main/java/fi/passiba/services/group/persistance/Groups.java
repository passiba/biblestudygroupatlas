package fi.passiba.services.group.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.persistance.Status;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import javax.persistence.AttributeOverride;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;

/**
 * Groups entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "groups")
@AttributeOverride(name = "id", column = @Column(name = "group_id"))
public class Groups extends AuditableEntity {

    // Fields
    // private Integer groupId;
    private Adress adress;
    // private Status status;
    private String name;
    private String congregationname;
    private String congregationwebsiteurl;
    private String congregatiolistemailaddress;
    private String description;
    private Set<Person> grouppersons = new HashSet<Person>(0);
    private Set<Biblesession> bibleSessions = new HashSet<Biblesession>(0);
    private String grouptypename;
    private String status;

    @Column(name = "grouptypename", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getGrouptypename() {
        return this.grouptypename;
    }

    public void setGrouptypename(String grouptypename) {
        this.grouptypename = grouptypename;
    }

    @Column(name = "status", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "description", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getDescription() {
        return description;
    }

    public void setDescription(String escription) {
        this.description = escription;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "groupsession",
    joinColumns = @JoinColumn(name = "fk_group_id", referencedColumnName = "group_id"),
    inverseJoinColumns =
    @JoinColumn(name = "fk_biblesessionid", referencedColumnName = "biblesessionid"))
    public Set<Biblesession> getBibleSessions() {
        return bibleSessions;
    }

    public void setBibleSessions(Set<Biblesession> bibleSessions) {
        this.bibleSessions = bibleSessions;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JoinTable(name = "groupperson",
    joinColumns = @JoinColumn(name = "fk_group_id", referencedColumnName = "group_id"),
    inverseJoinColumns =
    @JoinColumn(name = "fk_person_id", referencedColumnName = "person_id"))
    public Set<Person> getGrouppersons() {
        return grouppersons;
    }

    public void setGrouppersons(Set<Person> grouppersons) {
        this.grouppersons = grouppersons;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_group_adress_id", referencedColumnName = "adress_id")
    public Adress getAdress() {
        return this.adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    @Column(name = "name", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "congregationname", unique = false, nullable = false, insertable = true, updatable = true, length = 60)
    public String getCongregationname() {
        return this.congregationname;
    }

    public void setCongregationname(String congregationname) {
        this.congregationname = congregationname;
    }

    @Column(name = "congregationwebsiteurl", unique = false, nullable = true, insertable = true, updatable = true, length = 70)
    public String getCongregationwebsiteurl() {
        return this.congregationwebsiteurl;
    }

    public void setCongregationwebsiteurl(String congregationwebsiteurl) {
        this.congregationwebsiteurl = congregationwebsiteurl;
    }

    @Column(name = "congregatiolistemailaddress", unique = false, nullable = true, insertable = true, updatable = true, length = 70)
    public String getCongregatiolistemailaddress() {
        return this.congregatiolistemailaddress;
    }

    public void setCongregatiolistemailaddress(
            String congregatiolistemailaddress) {
        this.congregatiolistemailaddress = congregatiolistemailaddress;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Groups other = (Groups) obj;

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
