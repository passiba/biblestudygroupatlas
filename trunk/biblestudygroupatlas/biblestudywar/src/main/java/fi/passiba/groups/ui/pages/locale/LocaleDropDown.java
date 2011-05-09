/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package fi.passiba.groups.ui.pages.locale;

import java.util.List;
import java.util.Locale;

import org.apache.wicket.markup.html.form.ChoiceRenderer;
import org.apache.wicket.markup.html.form.DropDownChoice;
import org.apache.wicket.model.IModel;

public class LocaleDropDown extends DropDownChoice {

  private class LocaleRenderer extends ChoiceRenderer {

    @Override
    public String getDisplayValue(Object locale) {
      return ((Locale) locale).getDisplayName(getLocale());
    }
  }

  public LocaleDropDown(String id, List supportedLocales) {
    super(id, supportedLocales);
    setChoiceRenderer(new LocaleRenderer());
    setModel(new IModel() {

      public void detach() {
      }

      public Object getObject() {
        return getSession().getLocale();
      }

      public void setObject(Object object) {
        getSession().setLocale((Locale) object);
      }
    });
  }

  @Override
  protected boolean wantOnSelectionChangedNotifications() {
    return true;
  }
}
