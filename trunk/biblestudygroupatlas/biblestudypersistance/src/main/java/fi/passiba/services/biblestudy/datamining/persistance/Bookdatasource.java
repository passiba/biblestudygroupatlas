package fi.passiba.services.biblestudy.datamining.persistance;

import fi.passiba.hibernate.AuditableEntity;
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

    // Fields
    private String weburlName;
    private String status;
    private String scraperConfigFile;
    private String configFileDir;
    private String outputDir;
    private String outputSubDir;
    private String outputFileName;
    
    private Book book;
    
    
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
    @Column(name = "proxyHost", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getProxyHost() {
        return proxyHost;
    }

    public void setProxyHost(String proxyHost) {
        this.proxyHost = proxyHost;
    }
    @Column(name = "proxyPort", unique = false, nullable = false, insertable = true, updatable = true, length = 10)
    public String getProxyPort() {
        return proxyPort;
    }

    public void setProxyPort(String proxyPort) {
        this.proxyPort = proxyPort;
    }

    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_book_id", referencedColumnName = "book_id",nullable = true)
    public Book getBook() {
        return this.book;
    }

    public void setBook(Book book) {
        this.book = book;
    }
    @Column(name = "status", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getStatus() {
        return this.status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "weburlname", unique = false, nullable = false, insertable = true, updatable = true, length = 50)
    public String getWeburlName() {
        return weburlName;
    }

    public void setWeburlName(String weburlName) {
        this.weburlName = weburlName;
    }
    @Column(name = "configfiledir", unique = false, nullable = true, insertable = true, updatable = true, length = 50)
    public String getConfigFileDir() {
        return configFileDir;
    }

    public void setConfigFileDir(String configFileDir) {
        this.configFileDir = configFileDir;
    }
    @Column(name = "configfile", unique = false, nullable = true, insertable = true, updatable = true, length = 15)
    public String getScraperConfigFile() {
        return scraperConfigFile;
    }

    public void setScraperConfigFile(String scraperConfigFile) {
        this.scraperConfigFile = scraperConfigFile;
    }
    @Column(name = "outputdir", unique = false, nullable = true, insertable = true, updatable = true, length = 20)
    public String getOutputDir() {
        return outputDir;
    }

    public void setOutputDir(String outputDir) {
        this.outputDir = outputDir;
    }
     @Column(name = "outputfilename", unique = false, nullable = true, insertable = true, updatable = true, length = 15)
    public String getOutputFileName() {
        return outputFileName;
    }

    public void setOutputFileName(String outputFileName) {
        this.outputFileName = outputFileName;
    }
     @Column(name = "outputsubdir", unique = false, nullable = true, insertable = true, updatable = true, length = 15)
    public String getOutputSubDir() {
        return outputSubDir;
    }

    public void setOutputSubDir(String outputSubDir) {
        this.outputSubDir = outputSubDir;
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