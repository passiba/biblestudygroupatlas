package fi.passiba.groups.ui.pages.address;

import fi.passiba.services.address.IAddressService;
import fi.passiba.services.persistance.Adress;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.ajax.form.AjaxFormSubmitBehavior;
import org.apache.wicket.extensions.ajax.markup.html.autocomplete.AutoCompleteTextField;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.markup.html.form.RequiredTextField;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;
import org.apache.wicket.model.PropertyModel;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.apache.wicket.util.string.Strings;
import org.apache.wicket.validation.validator.StringValidator;

public class AddressPanel extends Panel {

    @SpringBean
    private IAddressService addressservice;
    private Adress address;

    public AddressPanel(String id, final long addressId) {
        super(id);
        setDefaultModel(new CompoundPropertyModel(new LoadableDetachableModel() {

            public Object load() {
                 address= addressservice.findAddressByAddressId(addressId);
                 return address;
            }
        }));
        AddressForm addForm = new AddressForm("addressForm", getDefaultModel());


        final RequiredTextField addr1 = new RequiredTextField("addr1", new PropertyModel(getDefaultModel(), "addr1"));
        addr1.add(StringValidator.maximumLength(80));
        addForm.add(addr1);


        final TextField addr2 = new TextField("addr2", new PropertyModel(getDefaultModel(), "addr2"));
        addr2.add(StringValidator.maximumLength(40));
        addForm.add(addr2);

        final RequiredTextField city = new RequiredTextField("city", new PropertyModel(getDefaultModel(), "city"));

        city.add(StringValidator.maximumLength(80));
        addForm.add(city);

        final AutoCompleteTextField country = new AutoCompleteTextField("country", new PropertyModel(getDefaultModel(), "country")) {

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
        };
        // add(new RequiredTextField("user.country").add(StringValidator.maximumLength(20)));
        country.setRequired(true);
        country.setOutputMarkupId(true);
        addForm.add(country);
        final Label selectedContry = new Label("selectedCountry", country.getDefaultModelObjectAsString());
        selectedContry.setOutputMarkupId(true);
        addForm.add(selectedContry);

        country.add(new AjaxFormSubmitBehavior("onchange") {

            @Override
            protected void onSubmit(AjaxRequestTarget target) {
                target.addComponent(selectedContry);
                target.addComponent(country);
            }

            @Override
            protected void onError(AjaxRequestTarget target) {
            }
        });
        final RequiredTextField zip = new RequiredTextField("zip", new PropertyModel(getDefaultModel(), "zip"));
        zip.add(StringValidator.maximumLength(80));
        addForm.add(zip);

        RequiredTextField state = new RequiredTextField("state", new PropertyModel(getDefaultModel(), "state"));
        state.add(StringValidator.maximumLength(80));
        addForm.add(state);



        add(addForm);
        //adding ajax form onchange method to form components
        addr1.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(AjaxRequestTarget target) {
                address.setAddr1(addr1.getDefaultModelObjectAsString());

            }
        });
        addr2.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(AjaxRequestTarget target) {

                address.setAddr2(addr2.getDefaultModelObjectAsString());

            }
        });
        city.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(AjaxRequestTarget target) {

                address.setCity(city.getDefaultModelObjectAsString());


            }
        });
        country.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(AjaxRequestTarget target) {

                address.setCountry(country.getDefaultModelObjectAsString());


            }
        });
        zip.add(new AjaxFormComponentUpdatingBehavior("onchange") {

            protected void onUpdate(AjaxRequestTarget target) {

                address.setZip(zip.getDefaultModelObjectAsString());

            }
        });
    }

    public Adress getAddress() {
        return address;
    }

    public void setAddress(Adress address) {
        this.address = address;
    }

    private class AddressForm extends Form {

        public AddressForm(String id, IModel m) {
            super(id, m);




        }
    }
}
