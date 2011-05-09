
package fi.passiba.groups.ui.pages.site;

import fi.passiba.services.bibledata.sword.HttpSwordInstaller;
import org.apache.wicket.IClusterable;
import org.crosswire.jsword.book.install.Installer;


/**
 * Domain class for Installer
 * 
 * @author haverinen
 */
public final class SiteInstaller{

    private String siteUpdateName;
    private HttpSwordInstaller  installer;

    public SiteInstaller()
    {
        super();
    }

    public SiteInstaller(String siteUpdate,Installer installer)
    {
         super();
        this.siteUpdateName=siteUpdate;
        this.installer=(HttpSwordInstaller )installer;

    }

    public HttpSwordInstaller  getInstaller() {
        return installer;
    }

    public void setInstaller(HttpSwordInstaller  installer) {
        this.installer = installer;
    }

    public String getSiteUpdateName() {
        return siteUpdateName;
    }

    public void setSiteUpdateName(String siteUpdateName) {
        this.siteUpdateName = siteUpdateName;
    }
       /**
     * @return the catologDirectory
     */
    public String getCatalogDirectory()
    {
        return installer.getCatalogDirectory();
    }

    /**
     * @param catologDirectory the catologDirectory to set
     */
    public void setCatalogDirectory(String catologDirectory)
    {
        this.installer.setCatalogDirectory(catologDirectory);
    }

    /**
     * @return Returns the directory.
     */
    public String getPackageDirectory()
    {
        return this.installer.getPackageDirectory();
    }

    /**
     * @param newDirectory The directory to set.
     */
    public void setPackageDirectory(String newDirectory)
    {
        this.installer.setPackageDirectory(newDirectory);
    }

    /**
     * @return the indexDirectory
     */
    public String getIndexDirectory()
    {
        return this.installer.getIndexDirectory();
    }

    /**
     * @param indexDirectory the indexDirectory to set
     */
    public void setIndexDirectory(String indexDirectory)
    {
        this.installer.setIndexDirectory(indexDirectory);
    }

    /**
     * @return Returns the host.
     */
    public String getHost()
    {
        return this.installer.getHost();
    }

    /**
     * @param newHost The host to set.
     */
    public void setHost(String newHost)
    {
       this.installer.setHost(newHost);
    }

    /**
     * @return Returns the proxyHost.
     */
    public String getProxyHost()
    {
        return this.installer.getProxyHost();
    }

    /**
     * @param newProxyHost The proxyHost to set.
     */
    public void setProxyHost(String newProxyHost)
    {
       this.installer.setProxyHost(newProxyHost);
    }

    /**
     * @return Returns the proxyPort.
     */
    public Integer getProxyPort()
    {
        return installer.getProxyPort();
    }

    /**
     * @param newProxyPort The proxyPort to set.
     */
    public void setProxyPort(Integer newProxyPort)
    {
        this.installer.setProxyPort(newProxyPort);
    }


    
}
