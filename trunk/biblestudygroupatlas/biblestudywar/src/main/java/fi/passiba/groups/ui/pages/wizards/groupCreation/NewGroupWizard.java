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
package fi.passiba.groups.ui.pages.wizards.groupCreation;

import fi.passiba.biblestudy.BibleStudyApplication;
import fi.passiba.groups.ui.model.Constants;
import fi.passiba.groups.ui.pages.Main;
import fi.passiba.groups.ui.pages.googlemap.GoogleMapsPanel;
import fi.passiba.groups.ui.pages.wizards.BaseAddressdModel;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;

import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;

import java.util.Locale;
import java.util.Set;
import org.apache.wicket.ResourceReference;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.extensions.wizard.StaticContentStep;
import org.apache.wicket.extensions.wizard.Wizard;
import org.apache.wicket.extensions.wizard.WizardModel;
import org.apache.wicket.extensions.wizard.WizardModel.ICondition;
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;

import org.apache.wicket.markup.html.panel.FeedbackPanel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.model.ResourceModel;
import org.apache.wicket.model.StringResourceModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.util.value.ValueMap;
import org.apache.wicket.validation.validator.EmailAddressValidator;
import org.apache.wicket.validation.validator.StringValidator;
import wicket.contrib.gmap.GMap2;
import wicket.contrib.gmap.GMapHeaderContributor;
import wicket.contrib.gmap.api.GClientGeocoder;
import wicket.contrib.gmap.api.GControl;
import wicket.contrib.gmap.api.GIcon;
import wicket.contrib.gmap.api.GInfoWindowTab;
import wicket.contrib.gmap.api.GLatLng;
import wicket.contrib.gmap.api.GMapType;
import wicket.contrib.gmap.api.GMarker;
import wicket.contrib.gmap.api.GMarkerOptions;
import wicket.contrib.gmap.api.GOverlay;
import wicket.contrib.gmap.api.GPoint;
import wicket.contrib.gmap.api.GSize;
import wicket.contrib.gmap.util.GeocoderException;

/**
 * This wizard shows some basic form use. It uses custom panels for the form elements, and a single
 * domain object ({@link User}) as it's subject. Also, the user roles step}is an optional step,
 * that will only be executed when assignRoles is true (and that value is edited in the user details
 * step).
 * 
 * @author Eelco Hillenius
 */
public class NewGroupWizard extends Wizard {

    @SpringBean
    private IAuthenticator authenticate;
    @SpringBean
    private IGroupServices groupservice;
    /**group types */
    private static final List<String> allGroupTypes = Constants.GroupType.getGroupTypes();
    /** Whether the assign Contactperson step should be executed. */
    private boolean assignContacperson = false;
    /** The group we are creating. */
    private Group group;
    private Person contactperson;

    /**
     * The confirmation step.
     */
    private final class ConfirmationStep extends StaticContentStep {

        /**
         * Construct.
         */
        public ConfirmationStep() {
            super(true);
            IModel userModel = new Model(group);
            setTitleModel(new ResourceModel("confirmation.title"));
            setSummaryModel(new StringResourceModel("confirmation.summary", this, userModel));
            setContentModel(new StringResourceModel("confirmation.content", this, userModel));
        }
    }

    /**
     * The Group details step.
     */
    private final class GroupDetailsStep extends WizardStep {

        /**
         * Construct.
         */
        public GroupDetailsStep() {
            setTitleModel(new ResourceModel("groupdetails.title"));
            setSummaryModel(new StringResourceModel("groupdetails.summary", this, new Model(group)));
            add(new RequiredTextField("group.name"));
            add(new RequiredTextField("group.phone"));
            add(new RequiredTextField("group.congregationname"));
            add(new RequiredTextField("group.congregationwebsiteurl"));
            add(new RequiredTextField("group.congregationemail").add(EmailAddressValidator.getInstance()));
            add(new CheckBox("assignContacperson"));
            
              WebMarkupContainer grouptypes = new WebMarkupContainer("grouptype");
            add(grouptypes);
            grouptypes.setVisible(true);
            grouptypes.add(new DropDownChoice("grouptypes", new PropertyModel(group,
                    "grouptype"), allGroupTypes).setRequired(true));

        }
    }

   

    /**
     * The Group Contactperson selection step.
     */
    private final class GroupContactPersonStep extends WizardStep implements ICondition {

        public GroupContactPersonStep(Person contactperson) {
            super(new ResourceModel("groupcontacperson.title"), null);
            setSummaryModel(new StringResourceModel("groupcontacperson.summary", this, new Model(group)));

            add(new ContactForm("contactform", getModel()));
            GoogleMapsPanel mapPanel = new GoogleMapsPanel("googleMapPanel", false);
            add(mapPanel);


        }

        public boolean evaluate() {
            return assignContacperson;
        }

        private class ContactForm extends Form {

            private List<Person> contactpersons = new ArrayList();
            private final ValueMap properties = new ValueMap();

            public ContactForm(String id, IModel m) {
                super(id, m);

                add(new TextField("contactcountry", new PropertyModel(properties, "contactcountry")) {

                    protected final void onComponentTag(final ComponentTag tag) {
                        super.onComponentTag(tag);
                        // clear the field after each render
                        tag.put("value", "");
                    }
                });

                add(new TextField("contactcity", new PropertyModel(properties, "contactcity")) {

                    protected final void onComponentTag(final ComponentTag tag) {
                        super.onComponentTag(tag);
                        // clear the field after each render
                        tag.put("value", "");
                    }
                });

                add(new Button("Find") {

                    public void onSubmit() {

                        contactpersons = authenticate.findPersonByRolename(Constants.RoleType.ADMIN.getType(), properties.getString("contactcountry"), properties.getString("contactcity"));

                    }
                });


                ChoiceRenderer choiceRenderer = new ChoiceRenderer("lastname");
                DropDownChoice contactpersonddc = new DropDownChoice("contactperson", new PropertyModel(contactperson, "lastname"),
                        new LoadableDetachableModel() {

                            @Override
                            protected Object load() {
                                if (properties.getString("contactcountry") == null || properties.getString("contactcountry").equals("")) {
                                    contactpersons = authenticate.findPersonByRolename(Constants.RoleType.ADMIN.getType(), group.getCountry(), group.getCity());
                                }
                                if (contactpersons != null && !contactpersons.isEmpty()) {
                                    contactperson = contactpersons.get(0);
                                }
                                return contactpersons;
                            }
                        }, choiceRenderer);
                add(contactpersonddc);

                //group.setAdress(new Adress());
                final Label selectedContactAddressAdd1 = new Label("selectedContactaddr1", new PropertyModel(contactperson, "adress.addr1"));
                selectedContactAddressAdd1.setOutputMarkupId(true);
                add(selectedContactAddressAdd1);

                final Label selectedContactAddressCity = new Label("selectedContactcity", new PropertyModel(contactperson, "adress.city"));
                selectedContactAddressCity.setOutputMarkupId(true);
                add(selectedContactAddressCity);

                final Label selectedContactAddressCountry = new Label("selectedContactcountry", new PropertyModel(contactperson, "adress.country"));
                selectedContactAddressCountry.setOutputMarkupId(true);
                add(selectedContactAddressCountry);


                contactpersonddc.add(new AjaxFormSubmitBehavior("onchange") {

                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        //group.setAdress(groupservice.findGroupAddressByGroupId(group.getId()));
                        selectedContactAddressAdd1.setModel(new PropertyModel(contactperson, "adress.addr1"));
                        selectedContactAddressCity.setModel(new PropertyModel(contactperson, "adress.city"));
                        selectedContactAddressCountry.setModel(new PropertyModel(contactperson, "adress.country"));
                        target.addComponent(selectedContactAddressAdd1);
                        target.addComponent(selectedContactAddressCity);
                        target.addComponent(selectedContactAddressCountry);
                    }

                    @Override
                    protected void onError(AjaxRequestTarget target) {
                    }
                });





            }
        }
    }

    /**
     * The user address step.
     */
    private final class GroupAddressStep extends WizardStep {

        /**
         * Construct.
         */
        public GroupAddressStep() {

           
            setTitleModel(new ResourceModel("addresstitle"));
            setSummaryModel(new StringResourceModel("address.summary", this, new Model(group)));
            
            
             GeoForm geocodeForm = new GeoForm("geocoder", getModel());
            final GMap2 bottomMap = new GMap2("bottomPanel",
                    new GMapHeaderContributor(BibleStudyApplication.get().getGoogleMapsAPIkey()));
            bottomMap.setOutputMarkupId(true);
            bottomMap.setMapType(GMapType.G_NORMAL_MAP);
            bottomMap.addControl(GControl.GSmallMapControl);
            bottomMap.setScrollWheelZoomEnabled(true);
            bottomMap.setZoom(9);
            bottomMap.setCenter(new GLatLng(60.226280212402344, 24.820398330688477));
            geocodeForm.add(bottomMap);

           


            final FeedbackPanel feedback = new FeedbackPanel("feedback");
            feedback.setOutputMarkupId(true);

            geocodeForm.add(feedback);

            geocodeForm.add(new TextField("group.addr2").add(StringValidator.maximumLength(40)));

            geocodeForm.add(new RequiredTextField("group.city").add(StringValidator.maximumLength(80)));
            //add(new RequiredTextField("group.country").add(StringValidator.maximumLength(20)));

            final AutoCompleteTextField country = new AutoCompleteTextField("group.country") {

                protected Iterator getChoices(String input) {
                    if (Strings.isEmpty(input)) {
                        return Collections.EMPTY_LIST.iterator();
                    }

                    List choices = new ArrayList(10);

                    Locale[] locales = Locale.getAvailableLocales();

                    for (int i = 0; i < locales.length; i++) {
                        final Locale locale = locales[i];
                        final String country = locale.getDisplayCountry();

                        if (country.toUpperCase().startsWith(input.toUpperCase())) {
                            choices.add(country);
                            if (choices.size() == 10) {
                                break;
                            }
                        }
                    }

                    return choices.iterator();
                }
                /* @Override
                protected Iterator getChoices(String arg0) {
                throw new UnsupportedOperationException("Not supported yet.");
                }*/
            };
            // add(new RequiredTextField("user.country").add(StringValidator.maximumLength(20)));
            country.setRequired(true);
            country.setOutputMarkupId(true);
            geocodeForm.add(country);
            final Label selectedContry = new Label("selectedCountry", country.getModelObjectAsString());
            selectedContry.setOutputMarkupId(true);
            geocodeForm.add(selectedContry);
            
            
            geocodeForm.add(new RequiredTextField("group.zip").add(StringValidator.maximumLength(80)));
            geocodeForm.add(new RequiredTextField("group.state").add(StringValidator.maximumLength(80)));
          
            RequiredTextField address = new RequiredTextField("group.addr1");
            address.add(StringValidator.maximumLength(80));
            geocodeForm.add(address);
            
            Button button = new Button("client");
            geocodeForm.add(button);
            
            add(geocodeForm);

            country.add(new AjaxFormSubmitBehavior("onchange") {

                @Override
                protected void onSubmit(AjaxRequestTarget target) {
                    target.addComponent(selectedContry);
                    target.addComponent(country);
                    group.setCountry(country.getModelObjectAsString());
                 
                }

                @Override
                protected void onError(AjaxRequestTarget target) {
                }
            });
            
             button.add(new GClientGeocoder("onclick", address,
                    BibleStudyApplication.get().getGoogleMapsAPIkey()) {

                @Override
                public void onGeoCode(AjaxRequestTarget target, int status,
                        String address, GLatLng latLng) {
                    if (status == GeocoderException.G_GEO_SUCCESS) {


                        group.setLat(latLng.getLat());
                        group.setLng(latLng.getLng());

                        bottomMap.setCenter(new GLatLng(latLng.getLat(), latLng.getLng()));
                        GIcon icon = new GIcon(urlFor(
                                new ResourceReference(NewGroupWizard.class, "groups.png")).toString(), urlFor(
                                new ResourceReference(NewGroupWizard.class, "shadow.png")).toString()).iconSize(new GSize(64, 64)).shadowSize(new GSize(64, 64)).iconAnchor(new GPoint(19, 40)).infoWindowAnchor(new GPoint(9, 2)).infoShadowAnchor(new GPoint(18, 25));
                        String addressInfo = "Haettu osoite";
                        GOverlay marker = new GMarker(new GLatLng(latLng.getLat(), latLng.getLng()),
                                new GMarkerOptions(addressInfo, icon));

                        bottomMap.addOverlay(marker);
                        bottomMap.getInfoWindow().open(
                                latLng,
                                new GInfoWindowTab(address, new Label(address,
                                address)));

                        target.addComponent(bottomMap);


                    } else {
                        error("Unable to geocode (" + status + ")");
                        target.addComponent(feedback);
                    }
                }
                ;
            });
           
            
        }

        private class GeoForm extends Form {

            public GeoForm(String id, IModel m) {
                super(id, m);




            }
        }
    }

    /**
     * Construct.
     * 
     * @param id
     *            The component id
     */
    public NewGroupWizard(String id) {
        super(id);
        contactperson = new Person();
        // create a blank user
        group = new Group();

        setModel(new CompoundPropertyModel(this));
        WizardModel model = new WizardModel();
        model.add(new GroupDetailsStep());
        model.add(new GroupAddressStep());
        model.add(new GroupContactPersonStep(contactperson));
        model.add(new ConfirmationStep());

        // initialize the wizard with the wizard model we just built
        init(model);
    }

    /**
     * @see org.apache.wicket.extensions.wizard.Wizard#onCancel()
     */
    public void onCancel() {
        setResponsePage(Main.class);
    }

    /**
     * @see org.apache.wicket.extensions.wizard.Wizard#onFinish()
     */
    public void onFinish() {
        if (group != null) {
            Groups newGroup = new Groups();
            Adress ad = new Adress();
            ad.setAddr1(group.getAddr1());
            if (group.getAddr2() != null) {
                ad.setAddr2(group.getAddr2());
            }
            ad.setCity(group.getCity());
            ad.setCountry(group.getCountry());
            ad.setPhone(group.getPhone());
            ad.setState(group.getState());
            ad.setZip(group.getZip());

            if (group.getLat() != 0 && group.getLng() != 0) {
                ad.setLocation_lat(group.getLat());
                ad.setLocation_lng(group.getLng());
            }
            newGroup.setAdress(ad);

            newGroup.setName(group.getName());
            newGroup.setCongregationname(group.getCongregationname());
            newGroup.setCongregatiolistemailaddress(group.getCongregationemail());
            newGroup.setCongregationwebsiteurl(group.getCongregationwebsiteurl());
            newGroup.setStatus(Constants.StatusType.ACTIVE.getType());
            newGroup.setGrouptypename(group.getGrouptype());
            groupservice.addGroup(newGroup);
            if (contactperson != null && contactperson.getLastname() != null &&
                    !contactperson.getLastname().trim().equals("") && (newGroup.getGrouppersons() == null || newGroup.getGrouppersons().isEmpty())) {


                Set<Person> contacts = new HashSet<Person>(0);
                Set<Groups> groupCol = new HashSet<Groups>(0);
                if (contactperson.getGroups() != null && !contactperson.getGroups().isEmpty()) {
                    groupCol = contactperson.getGroups();
                }
                groupCol.add(newGroup);
                contactperson.setGroups(groupCol);
                authenticate.updatePerson(contactperson);
                contacts.add(contactperson);
                newGroup.setGrouppersons(contacts);
                groupservice.updateGroup(newGroup);

            }

        }
        setResponsePage(Main.class);
    }

    public boolean isAssignContacperson() {
        return assignContacperson;
    }

    public void setAssignContacperson(boolean assignContacperson) {
        this.assignContacperson = assignContacperson;
    }
}