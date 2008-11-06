package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.hibernate.BaseEntity;
import fi.passiba.hibernate.DomainObject;
import fi.passiba.hibernate.Identifiable;
import java.util.HashSet;
import java.util.Set;
import javax.persistence.AttributeOverride;
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
import org.hibernate.search.annotations.Boost;
import org.hibernate.search.annotations.ContainedIn;
import org.hibernate.search.annotations.DocumentId;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.IndexedEmbedded;


/**
 * Chapter entity.
 * 
 * @author haverinen
 */
@Entity
@Table(name = "chapter")
//@AttributeOverride(name = "id", column = @Column(name = "chapter_id"))
@Indexed
public class Chapter implements DomainObject,Identifiable {

     // Fields

    private Long id;

    @Id
    @DocumentId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "chapter_id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Book book;
   
    private Integer chapterNum;
  
    private String chapterTitle;
   
    private Set<Verse> verses = new HashSet();

    private Biblesession session;


    @ContainedIn
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_biblesessionid", unique = false, nullable = true, insertable = true, updatable = true)
    public Biblesession getSession() {
        return session;
    }

    public void setSession(Biblesession session) {
        this.session = session;
    }
    
    @ContainedIn
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_book_id", unique = false, nullable = false, insertable = true, updatable = true)
    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    @Field(index=Index.UN_TOKENIZED)
    @Column(name = "chapterNum", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getChapterNum() {
        return this.chapterNum;
    }

    public void setChapterNum(Integer chapterNum) {
        this.chapterNum = chapterNum;
    }
    @Boost(1.0f)
    @Field(index = Index.TOKENIZED)
    @Column(name = "chapter_title", unique = false, nullable = false, insertable = true, updatable = true, length = 5000)
    public String getChapterTitle() {
        return this.chapterTitle;
    }

    public void setChapterTitle(String chapterTitle) {
        this.chapterTitle = chapterTitle;
    }
   // @ContainedIn
    @IndexedEmbedded
    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "chapter")
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
        final Chapter other = (Chapter) obj;

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