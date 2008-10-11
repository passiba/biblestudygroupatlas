package fi.passiba.services.biblestudy.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.hibernate.BaseEntity;
import fi.passiba.services.biblestudy.datamining.persistance.Bookdatasource;
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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Book entity.
 * 
 * @author MyEclipse Persistence Tools
 */
@Entity
@Table(name = "book")
@AttributeOverride(name = "id", column = @Column(name = "book_id"))
public class Book extends AuditableEntity {

    // Fields
    private Booksection booksection;
    private Integer bookNum;
    private String bookText;
    private Set<Chapter> chapters = new HashSet<Chapter>(0);
    private Bookdatasource source;

   
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_bookdatasource_id", unique = false, nullable = false, insertable = true, updatable = true)
    public Bookdatasource getsource() {
        return this.source;
    }

    public void setSource(Bookdatasource source) {
        this.source = source;
    }

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_section_id", unique = false, nullable = false, insertable = true, updatable = true)
    public Booksection getBooksection() {
        return this.booksection;
    }

    public void setBooksection(Booksection booksection) {
        this.booksection = booksection;
    }

    @Column(name = "bookNum", unique = false, nullable = true, insertable = true, updatable = true)
    public Integer getBookNum() {
        return this.bookNum;
    }

    public void setBookNum(Integer bookNum) {
        this.bookNum = bookNum;
    }

    @Column(name = "book_text", unique = false, nullable = false, insertable = true, updatable = true, length = 200)
    public String getBookText() {
        return this.bookText;
    }

    public void setBookText(String bookText) {
        this.bookText = bookText;
    }

    @OneToMany(cascade = {CascadeType.ALL}, fetch = FetchType.LAZY, mappedBy = "book")
    public Set<Chapter> getChapters() {
        return this.chapters;
    }

    public void setChapters(Set<Chapter> chapters) {
        this.chapters = chapters;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Book other = (Book) obj;

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