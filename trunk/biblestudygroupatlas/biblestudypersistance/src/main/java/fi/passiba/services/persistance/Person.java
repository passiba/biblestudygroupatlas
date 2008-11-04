package fi.passiba.services.persistance;

import fi.passiba.hibernate.DomainObject;
import fi.passiba.hibernate.Identifiable;
import fi.passiba.services.biblestudy.persistance.Biblesession;
import fi.passiba.services.group.persistance.Groups;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import javax.persistence.OneToOne;
import javax.persistence.Transient;
import org.compass.annotations.Cascade;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

/**
 * Person entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "person")
//@AttributeOverride(name = "id", column = @Column(name = "person_id"))
@Searchable
public class Person implements DomainObject,Identifiable {

    // Fields
    //private Integer personId;
    
    private Long id;
    @SearchableId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "person_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SearchableReference(cascade=Cascade.ALL)
    private Users fk_userid;
    @SearchableReference(cascade=Cascade.ALL)
    private Adress adress;
    @SearchableProperty(name="personal_email")
    @SearchableMetaData(name = "personemail")
    private String email;
    @SearchableProperty(name="firstname")
    private String firstname;
    @SearchableProperty(name="lastname")
    private String lastname;
    private String fullname;
    private String groupFirstName;
    private Date dateofbirth;
    @SearchableReference(cascade={Cascade.CREATE,Cascade.SAVE})
    private Set<Groups> groups = new HashSet<Groups>(0);
    private Set<Biblesession> bibleSessions = new HashSet<Biblesession>(0);

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_userid", referencedColumnName = "userid")
    public Users getFk_userid() {
        return fk_userid;
    }

    public void setFk_userid(Users fk_userid) {
        this.fk_userid = fk_userid;
    }

    @ManyToMany(cascade = CascadeType.MERGE, fetch = FetchType.EAGER)
    @JoinTable(name = "groupperson",
    joinColumns = @JoinColumn(name = "fk_person_id", referencedColumnName = "person_id"),
    inverseJoinColumns = @JoinColumn(name = "fk_group_id", referencedColumnName = "group_id"))
    public Set<Groups> getGroups() {
        return groups;
    }

    public void setGroups(Set<Groups> groups) {
        this.groups = groups;
    }

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "personsession",
    joinColumns = @JoinColumn(name = "fk_person_id", referencedColumnName = "person_id"),
    inverseJoinColumns =
    @JoinColumn(name = "fk_biblesessionid", referencedColumnName = "biblesessionid"))
    public Set<Biblesession> getBibleSessions() {
        return bibleSessions;
    }

    @Transient
    public String getGroupFirstName() {

        String name = "";

        for (Groups group : this.groups) {
            name = group.getName();
            break;
        }

        return name;
    }

    public void setBibleSessions(Set<Biblesession> bibleSessions) {
        this.bibleSessions = bibleSessions;
    }

    @Transient
    public String getFullname() {
        return this.firstname + " " + this.lastname;
    }

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_address_id", referencedColumnName = "adress_id")
    public Adress getAdress() {
        return this.adress;
    }

    public void setAdress(Adress adress) {
        this.adress = adress;
    }

    @Column(name = "email", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "firstname", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    @Column(name = "lastname", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getLastname() {
        return this.lastname;

    }

    public void setLastname(String lastname) {
        this.lastname = lastname;

    }

    @Temporal(TemporalType.DATE)
    @Column(name = "dateofbirth", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
    public Date getDateofbirth() {
        return this.dateofbirth;
    }

    public void setDateofbirth(Date dateofbirth) {
        this.dateofbirth = dateofbirth;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Person other = (Person) obj;

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
