package fi.passiba.services.biblestudy.datamining.persistance;

import fi.passiba.hibernate.AuditableEntity;
import fi.passiba.services.biblestudy.persistance.Bibletranslation;
import fi.passiba.services.biblestudy.persistance.Book;
import fi.passiba.services.persistance.Status;
import javax.persistence.AttributeOverride;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Bookdatasource entity....storing the information about the retreival of different
 * bible translation,installenr configuration with JSWord Integration
 * 
 * @author haverinen
 */
@Entity
@Table(name = "bookdatasource")
@AttributeOverride(name = "id", column = @Column(name = "bookdatasource_id"))
public class Bookdatasource extends AuditableEntity {


     
     
   
    //jsword specific variables
    private String sitename;
    private String hostname;
    private String catalogDir;
    private String packageDir;
    private String proxyHost;
    private String proxyPort;
    
    private String status;
   
    
    private Bibletranslation bibletranslation;
    
    
    @Column(name = "sitename", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getSitename() {
        return sitename;
    }

    public void setSitename(String sitename) {
        this.sitename = sitename;
    }
    // Constructors
    /** default constructor */


    @Column(name = "catalogDir", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getCatalogDir() {
        return catalogDir;
    }

    public void setCatalogDir(String catalogDir) {
        this.catalogDir = catalogDir;
    }
    @Column(name = "hostname", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }
    @Column(name = "packageDir", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getPackageDir() {
        return packageDir;
    }

    public void setPackageDir(String packageDir) {
        this.packageDir = packageDir;
    }
  
    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }
   
    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    


    @Column(name = "status", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getStatus() {
        return this.status;
    }
    
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_bible_translation_id", referencedColumnName = "bible_translation_id",nullable = true)
    public Bibletranslation getBibletranslation() {
        return bibletranslation;
    }

    public void setBibletranslation(Bibletranslation bibletranslation) {
        this.bibletranslation = bibletranslation;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    


    
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bookdatasource other = (Bookdatasource) obj;

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