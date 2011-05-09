package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.AuditableEntity;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Verse entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "chaptervoting")
@AttributeOverride(name = "id", column = @Column(name = "chaptervoting_id"))
public class ChapterVoting extends AuditableEntity {

    // Fields

   
    private Chapter chapter;
    private Integer totalscore=0;
    private Integer numberofvotes=0;
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    @JoinColumn(name = "fk_chapter_id", referencedColumnName = "chapter_id")
    public Chapter getChapter() {
        return this.chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }
    @Column(name = "numberofvotes", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getNumberofvotes() {
        return numberofvotes;
    }

    public void setNumberofvotes(Integer numberofvotes) {
        this.numberofvotes += numberofvotes;
    }
   @Column(name = "totalscore", unique = false, nullable = false, insertable = true, updatable = true)
    public Integer getTotalscore() {
        return totalscore;
    }

    public void setTotalscore(Integer totalscore) {
        this.totalscore += totalscore;
    }



    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ChapterVoting other = (ChapterVoting) obj;

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