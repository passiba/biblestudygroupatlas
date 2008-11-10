/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.pages;



import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.validation.validator.StringValidator;
import org.crosswire.jsword.book.install.InstallManager;
import org.crosswire.jsword.book.install.Installer;
import org.crosswire.jsword.book.install.sword.AbstractSwordInstaller;


/**
 *
 * @author haverinen
 */
public class SiteEdit extends BasePage implements SiteEditor{

     /**
     * The model that we are providing a view/controller for
     */
    private static InstallManager imanager;
    private  AbstractSwordInstaller installer;
    private String port,host,catalogDir,packageDir,proxyHost,proxyPort;

    private static final String INSTALLED_BOOKS_LABEL = "InstalledBooksLabel"; //$NON-NLS-1$
    private static final String AVAILABLE_BOOKS_LABEL = "AvailableBooksLabel"; //$NON-NLS-1$
    private static final String SELECTED_BOOK_LABEL = "SelectedBookLabel"; //$NON-NLS-1$
    private static final String REFRESH = "Refresh"; //$NON-NLS-1$
    private static final String INSTALL = "Install"; //$NON-NLS-1$
    private static final String INSTALL_SEARCH = "InstallSearch"; //$NON-NLS-1$
    private static final String DELETE = "Delete"; //$NON-NLS-1$
    private static final String UNLOCK = "Unlock"; //$NON-NLS-1$
    private static final String CHOOSE_FONT = "ChooseFont"; //$NON-NLS-1$
    private static final String UNINDEX = "Unindex"; //$NON-NLS-1$
    
    private Map installers=null;
  
      /**
     * A cache of the names in the Install Manager
     */
   private List names = new ArrayList();
  private final Page backPage;
  public SiteEdit()
   {
      backPage=null;
      init();
   }
    public SiteEdit(Page backPage) {

        this.backPage = backPage;
       /* setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {

                installers=imanager.getInstallers();
                return installers;
            }
        }));*/
        init();
    }

    private  void init() {

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
        String name="";
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
         setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {

               return getInstaller();
            }
        }));
        //add(new SiteForm("siteform", getModel(),name) );
        //setInstaller(installer);

    }
    private final class SiteForm extends Form {

        public SiteForm(String id, IModel m,String name) {
            super(id, m);

            Label siteName = new  Label("sitename", name);
            add(siteName);
            TextField hostName = new TextField("host", new PropertyModel(getModel(), "host"));
            hostName.setRequired(true);
            hostName.add(StringValidator.maximumLength(50));
            add(hostName);

            TextField catalogDir = new TextField("catalogDir", new PropertyModel(getModel(), "catalogDirectory"));
            catalogDir .setRequired(true);
            catalogDir .add(StringValidator.maximumLength(50));
            add(hostName);

           
            TextField packageDir = new TextField("packageDir", new PropertyModel(getModel(), "packageDirectory"));
            packageDir.add(StringValidator.maximumLength(50));
            add(packageDir);

            TextField proxyHost = new TextField("proxyHost", new PropertyModel(getModel(), "proxyHost"));
            proxyHost.add(StringValidator.maximumLength(50));
            add(proxyHost);

            TextField proxyPort = new TextField("proxyPort", new PropertyModel(getModel(), "proxyPort"));
            proxyPort.add(StringValidator.maximumLength(10));
            add(proxyPort);
            // ChoiceRenderer choiceRenderer = new ChoiceRenderer("fk_userid.rolename");
           
    
           
            add(new SaveButton("save"));
            add(new CancelButton("cancel"));
        }
    }

    private final class SaveButton extends Button {

      

        private SaveButton(String id) {
            super(id); 

        }

        @Override
        public void onSubmit() {
           // Person person = (Person) getForm().getModelObject();
            //person.setFk_userid(user);
            //authenticate.updatePerson(person);
             save();
             setResponsePage(SiteEdit.class);
            //setResponsePage(SiteEdit.this.backPage);
        }
    }
     private final class CancelButton extends Button {

        private static final long serialVersionUID = 1L;

        private CancelButton(String id) {
            super(id);

            setDefaultFormProcessing(false);
        }

        @Override
        public void onSubmit() {
            if(SiteEdit.this.backPage!=null)
            {
                setResponsePage(SiteEdit.this.backPage);
            }else
            {
                setResponsePage(Main.class);
            }
        }
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
        assert newInstaller == null || newInstaller instanceof AbstractSwordInstaller;
        Installer old = installer;
        installer = (AbstractSwordInstaller) newInstaller;
        if (newInstaller == null)
        {
            removeAll();
        }
        else if (!newInstaller.equals(old))
        {
            removeAll();
            init();
        }
    }
    
   
   

    /**
     * Pick an installer
     * @param parent A component to tie dialogs to
     * @return The chosen installer or null if the user cancelled.
     
    private static Installer selectInstaller(Component parent)
    {
        // Pick an installer
        InstallManager insman = new InstallManager();
        Map installers = insman.getInstallers();
        Installer installer = null;
        if (installers.size() == 1)
        {
            Iterator it = installers.values().iterator();
            boolean hasNext = it.hasNext();
            assert hasNext;
            installer = (Installer) it.next();
        }
        else
        {
            JComboBox choice = new JComboBox(new InstallManagerComboBoxModel(insman));
            JLabel label = new JLabel(Msg.HOW_SITE.toString());
            JPanel panel = new JPanel(new BorderLayout());
            panel.add(label, BorderLayout.NORTH);
            panel.add(choice, BorderLayout.CENTER);

            String title = Msg.HOW_SITE_TITLE.toString();

            int yn = CWOptionPane.showConfirmDialog(parent, panel, title, JOptionPane.YES_OPTION);
            if (yn == JOptionPane.YES_OPTION)
            {
                installer = (Installer) choice.getSelectedItem();
            }
        }

        return installer;
    }

 */
   


    
   
   
   


}
