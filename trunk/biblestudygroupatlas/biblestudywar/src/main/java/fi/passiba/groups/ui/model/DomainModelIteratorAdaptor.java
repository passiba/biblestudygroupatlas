package fi.passiba.groups.ui.model;

import fi.passiba.hibernate.DomainObject;
import java.util.Iterator;

import org.apache.wicket.markup.repeater.util.ModelIteratorAdapter;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.LoadableDetachableModel;

public class DomainModelIteratorAdaptor<T> extends ModelIteratorAdapter {

    public DomainModelIteratorAdaptor(
            Iterator<? extends DomainObject> delegate) {
        super(delegate);
    }

    @SuppressWarnings("unchecked")
    @Override
    protected IModel model(Object object) {
        final DomainObject domainObject = (DomainObject) (object);
        return new LoadableDetachableModel(domainObject) {

            @Override
            protected Object load() {
                return new DomainObjectModel(domainObject);
            }
        };

    }
}
