/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fi.passiba.groups.ui.pages.site;

import fi.passiba.groups.ui.pages.Main;
import fi.passiba.groups.ui.pages.BasePage;
import fi.passiba.services.bibledata.SiteEditor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.wicket.Page;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.crosswire.jsword.book.install.Installer;


/**
 *
 * @author haverinen
 */
public class SiteUpdateView extends BasePage {

    /**
     * The model that we are providing a view/controller for
     */
    @SpringBean
    private SiteEditor siteEditorService ;
 
      private final Page backPage;

    public SiteUpdateView() {
        super();
        backPage = null;
        init();
    }

    private void init() {

      
        if (siteEditorService != null &&siteEditorService.getInstallers() != null) {
         
        }

        final SiteForm form = new SiteForm("siteform");
        add(form);
        
    }
    private final class SiteForm extends Form {

        public SiteForm(String id) {
            super(id);

         

            final SiteBookTreePanel sitebookspanel = new SiteBookTreePanel("sitebookspanel");
            sitebookspanel.setOutputMarkupId(true);
            add(sitebookspanel);
            add(new CancelButton("cancel"));
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
            if (SiteUpdateView.this.backPage != null) {
                //  setResponsePage(SiteEdit.this.backPage);
            } else {
                setResponsePage(Main.class);
            }
        }
    }
}
