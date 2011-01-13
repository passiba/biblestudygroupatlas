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
package fi.passiba.groups.ui.pages.wizards.userCreation;

import fi.passiba.biblestudy.BibleStudyApplication;
import fi.passiba.biblestudy.BibleStudySession;
import fi.passiba.groups.ui.model.Constants;
import fi.passiba.groups.ui.pages.Main;
import fi.passiba.groups.ui.pages.googlemap.GoogleMapsPanel;
import fi.passiba.groups.ui.pages.wizards.captcha.CaptchaPanel;
import fi.passiba.services.address.IAddressService;
import fi.passiba.services.authenticate.IAuthenticator;
import fi.passiba.services.authenticate.PasswordService;
import fi.passiba.services.group.IGroupServices;
import fi.passiba.services.group.persistance.Groups;
import fi.passiba.services.persistance.Adress;
import fi.passiba.services.persistance.Person;
import fi.passiba.services.persistance.Users;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
import org.apache.wicket.extensions.wizard.WizardStep;
import org.apache.wicket.extensions.wizard.WizardModel.ICondition;
import org.apache.wicket.markup.ComponentTag;
import org.apache.wicket.markup.html.WebMarkupContainer;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Button;
import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.PasswordTextField;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.form.validation.EqualPasswordInputValidator;
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
public class NewUserWizard extends Wizard {
    @SpringBean
    private IAuthenticator authenticate;
    @SpringBean
    private IGroupServices groupservice;
    
     @SpringBean
    private IAddressService addressservice;

    /**
     * The confirmation step.
     */
    private final class ConfirmationStep extends StaticContentStep {

        /**
         * Construct.
         */
        public ConfirmationStep() {
            super(true);
            IModel<User> userModel = new Model<User>(user);
            setTitleModel(new ResourceModel("confirmation.title"));
            setSummaryModel(new StringResourceModel("confirmation.summary", this, userModel));
            setContentModel(new StringResourceModel("confirmation.content", this, userModel));
        }
    }

    /**
     * The user details step.
     */
    private final class UserDetailsStep extends WizardStep {

        /**
         * Construct.
         */
        public UserDetailsStep() {
            setTitleModel(new ResourceModel("confirmation.title"));
            setSummaryModel(new StringResourceModel("userdetails.summary", this, new Model(user)));
            add(new RequiredTextField("user.firstName"));
            add(new RequiredTextField("user.lastName"));
            add(new RequiredTextField("user.phone").add(StringValidator.maximumLength(40)));
            add(new CheckBox("assignGroups"));
        }
    }

    /**
     * The user name step.
     */
    private final class UserNameStep extends WizardStep {

        /**
         * Construct.
         */
        public UserNameStep() {
            super(new ResourceModel("username.title"), new ResourceModel("username.summary"));

            final PasswordTextField password = new PasswordTextField("user.password");
            password.setRequired(true);
            password.add(StringValidator.maximumLength(20));
            password.add(StringValidator.minimumLength(6));
            password.setResetPassword(false);
            add(password);


            final PasswordTextField confirmpassword = new PasswordTextField("user.confirmpassword");

            confirmpassword.setRequired(true);
            confirmpassword.add(StringValidator.maximumLength(20));
            confirmpassword.add(StringValidator.minimumLength(6));
            confirmpassword.setResetPassword(false);
            add(confirmpassword);
            add(new EqualPasswordInputValidator(password, confirmpassword));

            

            add(new RequiredTextField<String>("user.userName"));
            add(new RequiredTextField<String>("user.email").add(EmailAddressValidator.getInstance()));
            add(new CaptchaPanel("captchaPanel") {

                @Override
                public boolean isVisible() {
                    return true;
                }
            });

           WebMarkupContainer roles = new WebMarkupContainer("userrole");
            add(roles);
            roles.setVisible(true);
            roles.add(new DropDownChoice<String>("role", new PropertyModel<String>(user,
                    "roleName"), allRoles).setRequired(true));

           }
    }

   

    /**
     * The user address step.
     */
    private final class UserAddressStep extends WizardStep {

        /**
         * Construct.
         */
        public UserAddressStep() {
            setTitleModel(new ResourceModel("addresstitle"));
            setSummaryModel(new StringResourceModel("address.summary", this, new Model(user)));
            
            GeoForm geocodeForm = new GeoForm("geocoder", getDefaultModel());
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


            geocodeForm.add(new TextField<String>("user.addr2").add(StringValidator.maximumLength(40)));

            geocodeForm.add(new RequiredTextField<String>("user.city").add(StringValidator.maximumLength(80)));

            final AutoCompleteTextField country = new AutoCompleteTextField("user.country") {

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
            final Label selectedContry = new Label("selectedCountry", country.getDefaultModelObjectAsString());
            selectedContry.setOutputMarkupId(true);
            geocodeForm.add(selectedContry);

            RequiredTextField<String> address = new RequiredTextField("user.addr1");
            address.add(StringValidator.maximumLength(80));
            geocodeForm.add(address);

            geocodeForm.add(new RequiredTextField<String>("user.zip").add(StringValidator.maximumLength(80)));
            geocodeForm.add(new RequiredTextField<String>("user.state").add(StringValidator.maximumLength(80)));

            Button button = new Button("client");
            geocodeForm.add(button);
            country.add(new AjaxFormSubmitBehavior("onchange") {

                @Override
                protected void onSubmit(AjaxRequestTarget target) {
                    target.addComponent(selectedContry);
                    target.addComponent(country);
                    user.setCountry(country.getDefaultModelObjectAsString());
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


                        user.setLat(latLng.getLat());
                        user.setLng(latLng.getLng());

                        bottomMap.setCenter(new GLatLng(latLng.getLat(), latLng.getLng()));
                        GIcon icon = new GIcon(urlFor(
                                new ResourceReference(NewUserWizard.class, "image.gif")).toString(), urlFor(
                                new ResourceReference(NewUserWizard.class, "shadow.png")).toString()).iconSize(new GSize(64, 64)).shadowSize(new GSize(64, 64)).iconAnchor(new GPoint(19, 40)).infoWindowAnchor(new GPoint(9, 2)).infoShadowAnchor(new GPoint(18, 25));
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


            add(geocodeForm);

        }

        private class GeoForm extends Form {

            public GeoForm(String id, IModel m) {
                super(id, m);




            }
        }
    }

    /**
     * The Group Contactperson selection step.
     */
    private final class GroupSelectionStep extends WizardStep implements ICondition {


        public GroupSelectionStep(Groups group) {
            super(new ResourceModel("groupselection.title"), null);
            setSummaryModel(new StringResourceModel("groupselection.summary", this, new Model(group)));

            add(new GroupForm("groupform", getDefaultModel()));
            GoogleMapsPanel mapPanel = new GoogleMapsPanel("googleMapPanel", false);
            add(mapPanel);

        }

        public boolean evaluate() {
            return assignGroups;
        }

        private class GroupForm extends Form {

            private List<Groups> groupsCol = new ArrayList();
            private final ValueMap properties = new ValueMap();

            public GroupForm(String id, IModel m) {
                super(id, m);

                add(new TextField<String>("groupcountry", new PropertyModel<String>(properties, "groupcountry")) {

                    protected final void onComponentTag(final ComponentTag tag) {
                        super.onComponentTag(tag);
                        // clear the field after each render
                        tag.put("value", "");
                    }
                });

                add(new TextField<String>("groupcity", new PropertyModel<String>(properties, "groupcity")) {

                    protected final void onComponentTag(final ComponentTag tag) {
                        super.onComponentTag(tag);
                        // clear the field after each render
                        tag.put("value", "");
                    }
                });

                WebMarkupContainer grouptypes = new WebMarkupContainer("grouptype");
                add(grouptypes);
                grouptypes.setVisible(true);
                grouptypes.add(new DropDownChoice("grouptypes", new PropertyModel(properties,
                        "grouptype"), allGroupTypes));

                add(new Button("Find") {

                    public void onSubmit() {

                        groupsCol = groupservice.findGroupsByLocation(properties.getString("groupcountry"), properties.getString("groupcity"), properties.getString("grouptype"));

                    }
                });


                ChoiceRenderer choiceRenderer = new ChoiceRenderer("name");
                DropDownChoice<Groups> groupsddc = new DropDownChoice("group", new PropertyModel<String>(group, "name"),
                        new LoadableDetachableModel() {

                            @Override
                            protected Object load() {
                                if (properties.getString("groupcountry") == null || properties.getString("groupcountry").equals("")) {
                                    groupsCol = groupservice.findGroupsByLocation(user.getCountry(), user.getCity(), null);
                                }
                                if (groupsCol != null && !groupsCol.isEmpty()) {
                                    group = groupsCol.get(0);
                                    group.setAdress(addressservice.findAddressByAddressId(group.getAdress().getId()));
                                }
                                return groupsCol;
                            }
                        }, choiceRenderer);
                add(groupsddc);
                group.setAdress(new Adress());
                final Label selectedGroupAddressAdd1 = new Label("selectedGroupaddr1", new PropertyModel<String>(group, "adress.addr1"));
                selectedGroupAddressAdd1.setOutputMarkupId(true);
                add(selectedGroupAddressAdd1);

                final Label selectedGroupAddressCity = new Label("selectedGroupcity", new PropertyModel<String>(group, "adress.city"));
                selectedGroupAddressCity.setOutputMarkupId(true);
                add(selectedGroupAddressCity);

                final Label selectedGroupAddressCountry = new Label("selectedGroupcountry", new PropertyModel<String>(group, "adress.country"));
                selectedGroupAddressCountry.setOutputMarkupId(true);
                add(selectedGroupAddressCountry);


                groupsddc.add(new AjaxFormSubmitBehavior("onchange") {

                    @Override
                    protected void onSubmit(AjaxRequestTarget target) {
                        group.setAdress(addressservice.findAddressByAddressId(group.getAdress().getId()));
                        selectedGroupAddressAdd1.setDefaultModel(new PropertyModel<String>(group, "adress.addr1"));
                        selectedGroupAddressCity.setDefaultModel(new PropertyModel<String>(group, "adress.city"));
                        selectedGroupAddressCountry.setDefaultModel(new PropertyModel<String>(group, "adress.country"));
                        target.addComponent(selectedGroupAddressAdd1);
                        target.addComponent(selectedGroupAddressCity);
                        target.addComponent(selectedGroupAddressCountry);
                    }

                    @Override
                    protected void onError(AjaxRequestTarget target) {
                    }
                });



            }
        }
    }
    /** cheap roles database. */
    private static final List<String> allRoles =  Constants.RoleType.getRoleTypes();
    /**group types */
    private static final List<String>allGroupTypes = Constants.GroupType.getGroupTypes();
    
    /** Whether the assign roles or groupd assigment steps should be executed */
    private boolean assignRoles = false,  assignGroups = false;
    /** The user we are editing. */
    private User user;
    /** the assigned group */
    private Groups group;

    /**
     * Construct.
     * 
     * @param id
     *            The component id
     */
    public NewUserWizard(String id) {
        super(id);

        // create a blank user
        user = new User();
       
       // user.setUserName(BibleStudySession.get().);
        group = new Groups();
        setDefaultModel(new CompoundPropertyModel(this));
        WizardModel model = new WizardModel();
        model.add(new UserNameStep());
        model.add(new UserDetailsStep());
        model.add(new UserAddressStep());
        model.add(new GroupSelectionStep(group));
        model.add(new ConfirmationStep());

        // initialize the wizard with the wizard model we just built
        init(model);
    }

    /**
     * Gets user.
     * 
     * @return user
     */
    public User getUser() {
        return user;
    }

    /**
     * Gets assignRoles.
     * 
     * @return assignRoles
     */
    public boolean isAssignRoles() {
        return assignRoles;
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
        if (user != null) {
            Person person = new Person();
            Adress ad = new Adress();
            ad.setAddr1(user.getAddr1());
            if (user.getAddr2() != null) {
                ad.setAddr2(user.getAddr2());
            }
            ad.setCity(user.getCity());
            ad.setCountry(user.getCountry());
            ad.setPhone(user.getPhone());
            ad.setState(user.getState());
            ad.setZip(user.getZip());
            if (user.getLat() != 0 && user.getLng() != 0) {
                ad.setLocation_lat(user.getLat());
                ad.setLocation_lng(user.getLng());
            }
            person.setAdress(ad);

            person.setEmail(user.getEmail());
            person.setFirstname(user.getFirstName());
            person.setLastname(user.getLastName());
            person.setDateofbirth(new Date());




            Users regularuser = new Users();


            regularuser.setRolename(user.getRoleName());
            regularuser.setUsername(user.getUserName());
            try {
                regularuser.setPassword(PasswordService.encrypt(user.getUserName().toCharArray(), user.getPassword()));
            } catch (Exception ex) {
                
            }
            regularuser.setStatus(Constants.StatusType.ACTIVE.getType());
            person.setFk_userid(regularuser);



            authenticate.registerPerson(person);

            if (group != null && (group.getName() != null &&
                    !group.getName().equals("")) && (person.getGroups() == null || person.getGroups().isEmpty())) {


                List<Person> groupPersons = groupservice.findGroupsPersonsByGroupId(group.getId());
                Set<Groups> groups = new HashSet<Groups>(0);
                Set<Person> persons = new HashSet<Person>(0);
                for (Person per : groupPersons) {
                    persons.add(per);
                }
                persons.add(person);

                group.setGrouppersons(persons);
                groups.add(group);
                person.setGroups(groups);
                groupservice.updateGroup(group);

                authenticate.updatePerson(person);


            }



        }




        setResponsePage(Main.class);
    }

    /**
     * Sets assignRoles.
     * 
     * @param assignRoles
     *            assignRoles
     */
    public void setAssignRoles(boolean assignRoles) {
        this.assignRoles = assignRoles;
    }

    public boolean isAssignGroups() {
        return assignGroups;
    }

    public void setAssignGroups(boolean assignGroups) {
        this.assignGroups = assignGroups;
    }

    /**
     * Sets user.
     * 
     * @param user
     *            user
     */
    public void setUser(User user) {
        this.user = user;
    }
}