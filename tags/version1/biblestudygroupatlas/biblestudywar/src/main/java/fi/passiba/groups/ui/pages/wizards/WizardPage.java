
package fi.passiba.groups.ui.pages.wizards;

import java.lang.reflect.Constructor;

import org.apache.wicket.extensions.wizard.Wizard;

import fi.passiba.groups.ui.pages.DefaultBasePage;
import org.apache.wicket.util.string.Strings;



public class WizardPage extends  DefaultBasePage {

    /**
     * Construct.
     * 
     * @param <C>
     * 
     * @param wizardClass
     * class of the wizard component
     */
    public <C extends Wizard> WizardPage(Class<C> wizardClass) {
        if (wizardClass == null) {
            throw new IllegalArgumentException("argument wizardClass must be not null");
        }
        try {
            Constructor<? extends Wizard> ctor = wizardClass.getConstructor(String.class);
            Wizard wizard = ctor.newInstance("wizard");
            final String packageName = getClass().getPackage().getName();
            add(wizard);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
   
}
