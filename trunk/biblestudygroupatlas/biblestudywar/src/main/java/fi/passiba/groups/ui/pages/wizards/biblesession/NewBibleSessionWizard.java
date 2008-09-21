/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package fi.passiba.groups.ui.pages.wizards.biblesession;

import fi.passiba.groups.ui.pages.Home;
import fi.passiba.services.address.IAddressService;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.group.IGroupServices;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.wizard.StaticContentStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.DropDownChoice;

import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.validation.validator.StringValidator;
import org.wicketstuff.dojo.markup.html.form.DojoDatePicker;
import org.wicketstuff.dojo.toggle.DojoExplodeToggle;
import org.wicketstuff.dojo.toggle.DojoFadeToggle;
import org.wicketstuff.dojo.toggle.DojoWipeToggle;


import org.restlet.Client;
import org.restlet.resource.Representation;
import org.restlet.data.Method;
import org.restlet.data.Protocol;
import org.restlet.data.Request;
import org.restlet.data.Response;

/**
 * This wizard shows some basic form use. It uses custom panels for the form elements, and a single
 * domain object ({@link User}) as it's subject. Also, the user roles step}is an optional step,
 * that will only be executed when assignRoles is true (and that value is edited in the user details
 * step).
 * 
 * @author Eelco Hillenius
 */
public class NewBibleSessionWizard extends Wizard {

    @SpringBean
    private IAuthenticator authenticate;
    @SpringBean
    private IGroupServices groupservice;
    @SpringBean
    private static final List<String> allSessionTypes = Arrays.asList(new String[]{"Ryhmä", "Henkilö"});
    private BibleSession bibleSession = new BibleSession();

    /**
     * The confirmation step.
     */
    private final class ConfirmationStep extends StaticContentStep {

        /**
         * Construct.
         */
        public ConfirmationStep() {
            super(true);
            IModel sessionModel = new Model(bibleSession);
            setTitleModel(new ResourceModel("confirmation.title"));
            setSummaryModel(new StringResourceModel("confirmation.summary", this, sessionModel));
            setContentModel(new StringResourceModel("confirmation.content", this, sessionModel));
        }
    }

    /**
     * The Verse selection step.
     */
    private final class VerseSelectionStep extends WizardStep {

        /**
         * Construct.
         */
        public VerseSelectionStep() {
            setTitleModel(new ResourceModel("chapterselection.title"));
            setSummaryModel(new StringResourceModel("chapterselection.summary", this, new Model(bibleSession)));


            if (getModel() != null) {
                String weburl = (String) new PropertyModel(getModel(), "weburlName").getObject();
                bibleSession = (BibleSession) getModel().getObject();
                if (weburl != null) {
                    bibleSession.setBibleChapterText(getSelectedBibleVerseText(weburl));
                }
            }


            final Label selectedBibleChapterVerses = new Label("selectedChapterText", new PropertyModel(bibleSession, "bibleChapterText"));
            add(selectedBibleChapterVerses);

        }
    }

    /**
     * The BibleSession initialization step.
     */
    private final class SessionInitializationStep extends WizardStep {

        /**
         * Construct.
         */
        public SessionInitializationStep() {
            setTitleModel(new ResourceModel("sessiontitle"));
            setSummaryModel(new StringResourceModel("session.summary", this, new Model(bibleSession)));
            sessionForm form = new sessionForm("sessionform");
            add(form);


        }

        private class sessionForm extends Form {

            private sessionForm(String id) {
                super(id);

                DojoDatePicker sessionDate = new DojoDatePicker("sessionDate", new PropertyModel(bibleSession, "sessionDate"), "dd/MM/yyyy");

                sessionDate.setRequired(true);
                add(sessionDate);
                WebMarkupContainer sessiontypes = new WebMarkupContainer("sessiontype");
                add(sessiontypes);
                sessiontypes.setVisible(true);
                sessiontypes.add(new DropDownChoice("types", new PropertyModel(bibleSession,
                        "sessiontype"), allSessionTypes).setRequired(true));


                final AutoCompleteTextField weburlName = new AutoCompleteTextField("weburlName", new PropertyModel(bibleSession,
                        "weburlName")) {

                    @Override
                    protected Iterator getChoices(String arg0) {
                        return Collections.EMPTY_LIST.iterator();
                    }
                };
                weburlName.setOutputMarkupId(true);
                //weburlName.add(StringValidator.maximumLength(80));
                add(weburlName);
                final Label selectedBibleChapterVerses = new Label("selectedChapterText", new PropertyModel(bibleSession, "bibleChapterText"));
                selectedBibleChapterVerses.setOutputMarkupId(true);
                add(selectedBibleChapterVerses);
                weburlName.add(new AjaxFormSubmitBehavior("onchange") {

                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        bibleSession.setBibleChapterText(NewBibleSessionWizard.this.getSelectedBibleVerseText(weburlName.getModelObjectAsString()));
                        target.addComponent(selectedBibleChapterVerses);

                    }

                    @Override
                    protected void onError(AjaxRequestTarget target) {
                    }
                });

            }
        }
    }

    /**
     * Construct.
     * 
     * @param id
     *            The component id
     */
    public NewBibleSessionWizard(String id) {
        super(id);


        setModel(new CompoundPropertyModel(this));
        WizardModel model = new WizardModel();
        model.add(new SessionInitializationStep());
        model.add(new VerseSelectionStep());
        model.add(new ConfirmationStep());

        // initialize the wizard with the wizard model we just built
        init(model);
    }

    /**
     * @see org.apache.wicket.extensions.wizard.Wizard#onCancel()
     */
    public void onCancel() {
        setResponsePage(Home.class);
    }

    /**
     * @see org.apache.wicket.extensions.wizard.Wizard#onFinish()
     */
    public void onFinish() {





        setResponsePage(Home.class);
    }

    private String getSelectedBibleVerseText(String biblechapterWebRUL) {



        StringBuilder urlStringBuilder = new StringBuilder(biblechapterWebRUL);
        /*urlStringBuilder.append("http://www.gnpcb.org/esv/share/get/");
        urlStringBuilder.append("?key=IP");
        urlStringBuilder.append("&passage=" + URLEncoder.encode(book + " " + chapter, "ISO-8859-1"));
        urlStringBuilder.append("&action=doPassageQuery");
        urlStringBuilder.append("&include-headings=true");*/

        URL esvURL;
        InputStream esvStream;

        StringBuilder outStringBuilder = new StringBuilder();
        int nextChar;

        try {
            esvURL = new URL(urlStringBuilder.toString());
            esvStream = esvURL.openStream();
            while ((nextChar = esvStream.read()) != -1) {
                outStringBuilder.append((char) nextChar);
            }

            esvStream.close();

        } catch (MalformedURLException ex) {
           
        } catch (IOException ex) {
           
        }


        return outStringBuilder.toString();
    }
}