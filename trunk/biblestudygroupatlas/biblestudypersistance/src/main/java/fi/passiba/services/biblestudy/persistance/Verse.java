package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.hibernate.BaseEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Verse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "verse")
@AttributeOverride(name = "id", column = @Column(name = "verse_id"))
public class Verse extends AuditableEntity {

    // Fields

   
    private Chapter chapter;
    private Integer verseNum;
    private String verseText;
    private Biblesession session;

    

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_biblesessionid", unique = false, nullable = true, insertable = true, updatable = true)
    public Biblesession getSession() {
        return session;
    }

    public void setSession(Biblesession session) {
        this.session = session;
    }

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