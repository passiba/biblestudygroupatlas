/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.groups.ui.pages.site;

import fi.passiba.groups.ui.pages.Main;
import fi.passiba.groups.ui.pages.ProtectedPage;
import fi.passiba.services.bibledata.SiteEditor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.crosswire.jsword.book.install.Installer;


/**
 *
 * @author haverinen
 */
public class SiteUpdateEdit extends ProtectedPage {

    /**
     * The model that we are providing a view/controller for
     */
    @SpringBean
    private SiteEditor siteEditorService ;
    private  SiteInstaller installerValues=new SiteInstaller ();

    private String host,catalogDir,packageDir,proxyHost,proxyPort;
   
    private Map installers = null;

    private List<SiteInstaller>siteIntstallers=new ArrayList();
    /**
     * A cache of the names in the Install Manager
     */
    private List names = new ArrayList();
    private final Page backPage;

    public SiteUpdateEdit() {
        super();
        backPage = null;
        init();
    }

    private void init() {

      
        if (siteEditorService != null &&siteEditorService.getInstallers() != null) {
            installers = siteEditorService.getInstallers();
            names.clear();
            names.addAll(siteEditorService.getInstallers().keySet());
            Collections.sort(names);
        }

        /*  InstallerFactory ifactory = imanager.getInstallerFactory(type);
        Installer installer = ifactory.createInstaller();*/
        String name = "";
        if (installers != null) {
            Iterator iter = installers.keySet().iterator();
            while (iter.hasNext()) {
                name = (String) iter.next();
                Installer installer = (Installer) installers.get(name);
                SiteInstaller siteValues=new SiteInstaller(name,installer);
                siteIntstallers.add(installerValues);
                setInstaller(siteValues);
               // break;
            // SitePane site = new SitePane(installer);
            //  tabMain.add(name, site);
            }
        }
        setModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            protected Object load() {

                return getInstaller();
            }
        }));
        final SiteForm form = new SiteForm("siteform", getModel(), name);
        add(form);

    //setInstaller(installer);

    }

    private final class SiteForm extends Form {

        public SiteForm(String id, IModel m, String name) {
            super(id, m);

            final Label siteName = new Label("sitename", new PropertyModel(getModel(), "siteUpdateName"));
            siteName.setOutputMarkupId(true);
            add(siteName);
            final TextField hostName = new TextField("host", new PropertyModel(getModel(), "installer.host"));
            hostName.setRequired(true);
            hostName.add(StringValidator.maximumLength(50));
            hostName.setOutputMarkupId(true);
            add(hostName);

            final TextField catalogDir = new TextField("catalogDir", new PropertyModel(getModel(), "installer.catalogDirectory"));
            catalogDir.setRequired(true);
            catalogDir.add(StringValidator.maximumLength(50));
            catalogDir.setOutputMarkupId(true);
            add(catalogDir);


            final TextField packageDir = new TextField("packageDir", new PropertyModel(getModel(), "installer.packageDirectory"));
            packageDir.add(StringValidator.maximumLength(50));
            packageDir.setOutputMarkupId(true);
            add(packageDir);

            ChoiceRenderer choiceRenderer = new ChoiceRenderer("siteUpdateName");

            DropDownChoice siteinstallers = new DropDownChoice("installers", new PropertyModel(installerValues, "siteUpdateName"),
                    new LoadableDetachableModel() {

                        @Override
                        protected Object load() {

                            for(SiteInstaller ins:siteIntstallers)
                            {
                                if(ins!=null && 
                                        ins.getSiteUpdateName()!=null &&
                                        installerValues!=null && installerValues.getSiteUpdateName()!=null
                                        && ins.getSiteUpdateName().equals(installerValues.getSiteUpdateName()))
                                {
                                    installerValues=ins;
                                }
                            }
                            return siteIntstallers;

                        }
                    },choiceRenderer);

            siteinstallers.setOutputMarkupId(true);
            add(siteinstallers);

            siteinstallers.add(new AjaxFormSubmitBehavior("onchange") {

                @Override
                protected void onSubmit(AjaxRequestTarget target) {
                    //group.setAdress(groupservice.findGroupAddressByGroupId(group.getId()));
                    hostName.setModel(new PropertyModel( installerValues, "host"));
                    catalogDir.setModel(new PropertyModel( installerValues, "catalogDirectory"));
                    packageDir.setModel(new PropertyModel( installerValues, "packageDirectory"));
                    siteName.setModel(new PropertyModel( installerValues, "packageDirectory"));
                    target.addComponent(siteName);
                    target.addComponent(hostName);
                    target.addComponent(catalogDir);
                    target.addComponent(packageDir);
                    reset();
                }

                @Override
                protected void onError(AjaxRequestTarget target) {
                }
            });

            /*  TextField proxyHost = new TextField("proxyHost", new PropertyModel(getModel(), "proxyHost"));
            proxyHost.add(StringValidator.maximumLength(50));
            add(proxyHost);

            TextField proxyPort = new TextField("proxyPort", new PropertyModel(getModel(), "proxyPort"));
            proxyPort.add(StringValidator.maximumLength(10));
            add(proxyPort);*/
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
            setResponsePage(SiteUpdateEdit.class);
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
            if (SiteUpdateEdit.this.backPage != null) {
                //  setResponsePage(SiteEdit.this.backPage);
            } else {
                setResponsePage(Main.class);
            }
        }
    }

    public void save() {
        if (installerValues == null) {
            return;
        }
        installerValues.setHost(host);
        installerValues.setCatalogDirectory(catalogDir);
        installerValues.setPackageDirectory(packageDir);
        installerValues.setProxyHost(proxyHost);
        Integer pport = null;
        try {
            pport = new Integer(proxyPort);
        } catch (NumberFormatException e) {
            pport = null; // or -1
        }
        installerValues.setProxyPort(pport);
    }

    public void reset() {
        if (installerValues == null) {
            return;
        }

        host = (installerValues.getHost());
        catalogDir = (installerValues.getCatalogDirectory());
        packageDir = (installerValues.getPackageDirectory());
        proxyHost = (installerValues.getProxyHost());
        Integer port = installerValues.getProxyPort();
        proxyPort = (port == null ? null : port.toString());
    }


    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.book.install.SiteEditor#getInstaller()
     */
    public SiteInstaller getInstaller() {
        return this.installerValues;
    }

    /* (non-Javadoc)
     * @see org.crosswire.bibledesktop.book.install.SiteEditor#setInstaller()
     */
    public void setInstaller(SiteInstaller newInstaller) {
       
        this.installerValues=newInstaller;
        
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
