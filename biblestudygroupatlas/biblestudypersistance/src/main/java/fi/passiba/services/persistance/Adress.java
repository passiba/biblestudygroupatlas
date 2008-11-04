package fi.passiba.services.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.hibernate.BaseEntity;
import fi.passiba.hibernate.DomainObject;
import fi.passiba.hibernate.Identifiable;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;


import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;

/**
 * Adress entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "adress")
@Searchable
//@AttributeOverride(name = "id", column = @Column(name = "adress_id"))
public class Adress implements DomainObject,Identifiable  {

    private Long id;
    @SearchableId
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "adress_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @SearchableProperty(name="addr1")
    @SearchableMetaData(name = "Address 1")
    private String addr1;
    @SearchableProperty(name="addr2")
    @SearchableMetaData(name = "Address 2")
    private String addr2;
    @SearchableProperty(name="city")
    @SearchableMetaData(name = "town")
    private String city;
    @SearchableProperty(name="state")
    private String state;
    @SearchableProperty(name="zip")
    @SearchableMetaData(name = "zip code")
    private String zip;

    @SearchableProperty(name="country")
    private String country;

    @SearchableProperty(name="phone")
    @SearchableMetaData(name = "phonenumber")
    private String phone;

    @SearchableProperty(name="location_lat")
    private double location_lat;
    @SearchableProperty(name="location_lng")
    private double location_lng;

   

    @Column(name = "addr1", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getAddr1() {
        return this.addr1;
    }

    public void setAddr1(String addr1) {
        this.addr1 = addr1;
    }

    @Column(name = "addr2", unique = false, nullable = true, insertable = true, updatable = true, length = 40)
    public String getAddr2() {
        return this.addr2;
    }

    public void setAddr2(String addr2) {
        this.addr2 = addr2;
    }

    @Column(name = "city", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "state", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "zip", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
    public String getZip() {
        return this.zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    @Column(name = "country", unique = false, nullable = false, insertable = true, updatable = true, length = 20)
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Column(name = "phone", unique = false, nullable = false, insertable = true, updatable = true, length = 80)
    public String getPhone() {
        return this.phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Column(name = "location_lat", unique = false, nullable = true, insertable = true, updatable = true)
    public double getLocation_lat() {
        return location_lat;
    }

    public void setLocation_lat(double location_lat) {
        this.location_lat = location_lat;
    }

    @Column(name = "location_lng", unique = false, nullable = true, insertable = true, updatable = true)
    public double getLocation_lng() {
        return location_lng;
    }

    public void setLocation_lng(double location_lng) {
        this.location_lng = location_lng;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Adress other = (Adress) obj;

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
