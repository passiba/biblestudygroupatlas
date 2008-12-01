/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.services.bibledata;




import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.crosswire.jsword.book.install.Installer;
import fi.passiba.services.bibledata.sword.HttpSwordInstaller;
import org.springframework.stereotype.Service;


/**
 *
 * @author haverinen
 */
//@Service("BibleDataService")
public class SiteDataService implements SiteEditor{

     /**
     * The model that we are providing a view/controller for
     */
    private static InstallManager imanager;
    private  HttpSwordInstaller installer;
    private String port,host,catalogDir,packageDir,proxyHost,proxyPort;
  
    private Map installers=null;
  
      /**
     * A cache of the names in the Install Manager
     */
   private List names = new ArrayList();
 
  public SiteDataService(String name)
   {
    
      init(name);
   }
   public SiteDataService()
   {

      init(null);
   }
    private  void init(String name) {

        if(imanager ==null)
        {
           imanager  = new InstallManager();
        }
        installers = imanager.getInstallers();
        names.clear();
        names.addAll(imanager.getInstallers().keySet());
        Collections.sort(names);

        host="";
        catalogDir="";
        packageDir="";
        proxyHost="";
        proxyPort="";
        String type="";
      /*  InstallerFactory ifactory = imanager.getInstallerFactory(type);
        Installer installer = ifactory.createInstaller();*/
        if(name==null || name.isEmpty())
        {
            Iterator iter = installers.keySet().iterator();
            while (iter.hasNext())
            {
                name = (String) iter.next();
                Installer installer = (Installer) installers.get(name);
                setInstaller(installer);
             break;
           // SitePane site = new SitePane(installer);
          //  tabMain.add(name, site);
            }
        }else
        {
           Installer installer = (Installer) installers.get(name);
           setInstaller(installer);
        }
         
        //add(new SiteForm("siteform", getModel(),name) );
        //setInstaller(installer);

    }
 

    
     
    @Override
    public void save() {
        if (installer == null)
        {
            return;
        }
        installer.setHost(host);
        installer.setCatalogDirectory(catalogDir);
        installer.setPackageDirectory(packageDir);
        installer.setProxyHost(proxyHost);
        Integer pport = null;
        try
        {
            pport = new Integer(proxyPort);
        }
        catch (NumberFormatException e)
        {
            pport = null; // or -1
        }
        installer.setProxyPort(pport);
    }

    @Override
    public void reset() {
            if (installer == null)
        {
            return;
        }

        host=(installer.getHost());
        catalogDir=(installer.getCatalogDirectory());
        packageDir=(installer.getPackageDirectory());
        proxyHost=(installer.getProxyHost());
        Integer port = installer.getProxyPort();
        proxyPort=(port == null ? null : port.toString());
    }
     

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.book.install.SiteEditor#getInstaller()
     */
    public Installer getInstaller()
    {
        return installer;
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.book.install.SiteEditor#setInstaller()
     */
    public void setInstaller(Installer newInstaller)
    {
        assert newInstaller == null || newInstaller instanceof HttpSwordInstaller;
        Installer old = installer;
        installer = (HttpSwordInstaller) newInstaller;
        if (newInstaller == null)
        {
           
        }
        else if (!newInstaller.equals(old))
        {
           
           // init();
        }
    }

    public Map getInstallers()
    {
        return installers;
    }

    @Override
    public Installer getInstaller(String sitename) {
        Installer installer = (Installer) installers.get(sitename);
        return installer;
    }

    




   




    
   
   
   


}
