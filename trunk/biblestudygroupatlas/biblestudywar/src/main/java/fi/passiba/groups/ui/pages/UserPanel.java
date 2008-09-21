package fi.passiba.groups.ui.pages;

import fi.passiba.groups.ui.pages.locale.LocaleDropDown;
import fi.passiba.groups.ui.pages.user.EditPersonContact;
import fi.passiba.services.persistance.Person;
import java.util.Arrays;
import java.util.Locale;
import org.apache.wicket.PageParameters;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.PropertyModel;

public class UserPanel extends Panel {

    public UserPanel(String id) {

        super(id);
      
        add(new Label("groups", new PropertyModel(this,
                "session.person.groupFirstName")));

        add(new LocaleDropDown("localeSelect", Arrays.asList(new Locale[]{Locale.ENGLISH, new Locale("fi")})));
        
        
        Link edit=new Link("edit", new PropertyModel(this,
                "session.person")) {
            public void onClick() {
                Person p = (Person) getModelObject();
                setResponsePage(new EditPersonContact(getPage(),p.getId()));
            }
        };
        edit.add(new Label("fullname", new PropertyModel(this,
                "session.person.fullname")));
        add(edit);
  }
  
                      
}
