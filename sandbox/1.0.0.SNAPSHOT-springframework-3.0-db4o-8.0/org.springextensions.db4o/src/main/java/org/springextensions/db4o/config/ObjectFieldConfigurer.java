package org.springextensions.db4o.config;

import com.db4o.config.ObjectClass;
import com.db4o.config.ObjectField;

/**
 * author: olli
 */
public class ObjectFieldConfigurer {

    private ObjectField objectField;

    public ObjectFieldConfigurer(ObjectClass objectClass, String field) {
        objectField = objectClass.objectField(field);
    }

    public ObjectField getObjectField() {
        return objectField;
    }

    /**
     * @see com.db4o.config.ObjectField#cascadeOnActivate(boolean)
     */
    public void setCascadeOnActivate(boolean cascadeOnActivate) {
        objectField.cascadeOnActivate(cascadeOnActivate);
    }

    /**
     * @see com.db4o.config.ObjectField#cascadeOnDelete(boolean)
     */
    public void setCascadeOnDelete(boolean cascadeOnDelete) {
        objectField.cascadeOnDelete(cascadeOnDelete);
    }

    /**
     * @see com.db4o.config.ObjectField#cascadeOnUpdate(boolean)
     */
    public void setCascadeOnUpdate(boolean cascadeOnUpdate) {
        objectField.cascadeOnUpdate(cascadeOnUpdate);
    }

    /**
     * @see com.db4o.config.ObjectField#indexed(boolean)
     */
    public void setIndexed(boolean indexed) {
        objectField.indexed(indexed);
    }

    /**
     * @see com.db4o.config.ObjectField#rename(String)
     */
    public void setRename(String rename) {
        objectField.rename(rename);
    }

}
