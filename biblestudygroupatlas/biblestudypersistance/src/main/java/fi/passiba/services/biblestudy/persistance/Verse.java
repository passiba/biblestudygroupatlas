package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.hibernate.BaseEntity;
import fi.passiba.hibernate.DomainObject;
import fi.passiba.hibernate.Identifiable;
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
import javax.persistence.Table;
import org.compass.annotations.Cascade;
import org.compass.annotations.Searchable;
import org.compass.annotations.SearchableId;
import org.compass.annotations.SearchableMetaData;
import org.compass.annotations.SearchableProperty;
import org.compass.annotations.SearchableReference;

/**
 * Verse entity.
 * 
 * @author haverinen
 */
@Entity
@Table(name = "verse")
//@AttributeOverride(name = "id", column = @Column(name = "verse_id"))
@Searchable
public class Verse implements DomainObject,Identifiable  {

    // Fields
    @Id
    @SearchableId
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "verse_id")
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    private Chapter chapter;
    @SearchableProperty(name="versenumber")
    @SearchableMetaData(name = "versenum")
    private Integer verseNum;

    @SearchableProperty(name="versetext")
    @SearchableMetaData(name = "text")
    private String verseText;
   

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_chapter_id", unique = false, nullable = false, insertable = true, updatable = true)
    public Chapter getChapter() {
        return this.chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Column(name = "verse_num", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getVerseNum() {
        return this.verseNum;
    }

    public void setVerseNum(Integer verseNum) {
        this.verseNum = verseNum;
    }

    @Column(name = "verse_text", unique = false, nullable = false, insertable = true, updatable = true, length = 9000)
    public String getVerseText() {
        return this.verseText;
    }

    public void setVerseText(String verseText) {
        this.verseText = verseText;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Verse other = (Verse) obj;

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