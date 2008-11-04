package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.hibernate.BaseEntity;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.compass.annotations.Cascade;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

/**
 * Booksection entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "booksection")
@AttributeOverride(name = "id", column = @Column(name = "section_id"))

public class Booksection extends AuditableEntity {

    // Fields
   
    private Bibletranslation bibletranslation;
   
    private String section;
    private Set<Book> books = new HashSet<Book>(0);

    

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_bible_translation_id")
    public Bibletranslation getBibletranslation() {
        return this.bibletranslation;
    }

    public void setBibletranslation(Bibletranslation bibletranslation) {
        this.bibletranslation = bibletranslation;
    }

    @Column(name = "section", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }

    @OneToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY, mappedBy = "booksection")
    public Set<Book> getBooks() {
        return this.books;
    }

    public void setBooks(Set<Book> books) {
        this.books = books;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Booksection other = (Booksection) obj;

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