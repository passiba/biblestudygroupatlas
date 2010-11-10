package fi.passiba.groups.ui.model;

import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;


public class HashcodeEnabledCompoundPropertyModel extends CompoundPropertyModel {

    public HashcodeEnabledCompoundPropertyModel(Object object) {
        super(object);
    }

    @Override
    public int hashCode() {
        return this.hashCodeCompare(getObject());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof IModel) {
            return this.equalCompare(getObject(), ((IModel) obj).getObject());
        }
        return false;
    }

    private boolean equalCompare(Object lhs, Object rhs) {
        if (lhs == rhs) {
            return true;
        }
        if (lhs == null) {
            return false;
        }
        return lhs.equals(rhs);
    }

    private int hashCodeCompare(final Object... obj) {
        int result = 37;
        for (Object o : obj) {
            result = 37 * result + (o != null ? o.hashCode() : 0);
        }
        return result;
    }
}
