package fi.passiba.services.biblestudy.persistance;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.IndexedEmbedded;

import fi.passiba.hibernate.DomainObject;
import fi.passiba.hibernate.Identifiable;


/**
 * Booksection entity.
 * 
 * @author haverinen
 */
@Entity
@Table(name = "booksection")
//@AttributeOverride(name = "id", column = @Column(name = "section_id"))
//@Indexed
public class Booksection implements DomainObject,Identifiable {

    // Fields
    private Long id;

    @Id
    //@DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "section_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    private Bibletranslation bibletranslation;
   
    private String section;
    private Set<Book> books = new HashSet<Book>(0);

    
    @IndexedEmbedded
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_bible_translation_id")
    public Bibletranslation getBibletranslation() {
        return this.bibletranslation;
    }

    public void setBibletranslation(Bibletranslation bibletranslation) {
        this.bibletranslation = bibletranslation;
    }
    @Field(index = Index.UN_TOKENIZED)
    @Column(name = "section", unique = false, nullable = false, insertable = true, updatable = true, length = 40)
    public String getSection() {
        return this.section;
    }

    public void setSection(String section) {
        this.section = section;
    }
   // @ContainedIn
    //@IndexedEmbedded
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