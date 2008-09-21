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
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Bibletranslation entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "bibletranslation")
@AttributeOverride(name = "id", column = @Column(name = "bible_translation_id"))
public class Bibletranslation extends AuditableEntity {

    // Fields
    private String bibleAbbrv;
    private String bibleName;
    private String publisherName;
    private Date publishedDate;
    private Set<Booksection> booksections = new HashSet<Booksection>(0);

   

    @Column(name = "bible_Abbrv", unique = false, nullable = false, insertable = true, updatable = true, length = 70)
    public String getBibleAbbrv() {
        return this.bibleAbbrv;
    }

    public void setBibleAbbrv(String bibleAbbrv) {
        this.bibleAbbrv = bibleAbbrv;
    }

    @Column(name = "bible_name", unique = false, nullable = false, insertable = true, updatable = true, length = 200)
    public String getBibleName() {
        return this.bibleName;
    }

    public void setBibleName(String bibleName) {
        this.bibleName = bibleName;
    }

    @Column(name = "publisher_name", unique = false, nullable = false, insertable = true, updatable = true, length = 100)
    public String getPublisherName() {
        return this.publisherName;
    }

    public void setPublisherName(String publisherName) {
        this.publisherName = publisherName;
    }

    @Temporal(TemporalType.DATE)
    @Column(name = "published_date", unique = false, nullable = false, insertable = true, updatable = true, length = 0)
    public Date getPublishedDate() {
        return this.publishedDate;
    }

    public void setPublishedDate(Date publishedDate) {
        this.publishedDate = publishedDate;
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "bibletranslation")
    public Set<Booksection> getBooksections() {
        return this.booksections;
    }

    public void setBooksections(Set<Booksection> booksections) {
        this.booksections = booksections;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bibletranslation other = (Bibletranslation) obj;

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