package org.springextensions.db4o.config;

import com.db4o.config.ObjectClass;
import com.db4o.config.ObjectField;
import com.db4o.io.PagingMemoryStorage;
import org.springextensions.db4o.example.Person;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * author: olli
 */
// TODO: write proper tests
public class ObjectFieldConfigurerTest {

    @Test
    public void testObjectField() throws Exception {
        EmbeddedConfigurationFactoryBean embeddedConfigurationFactoryBean = new EmbeddedConfigurationFactoryBean();
        embeddedConfigurationFactoryBean.getFile().setStorage(new PagingMemoryStorage());

        ObjectClassConfigurer objectClassConfigurer = new ObjectClassConfigurer(embeddedConfigurationFactoryBean.getCommon(), Person.class);
        ObjectClass objectClass = objectClassConfigurer.getObjectClass();

        ObjectFieldConfigurer objectFieldConfigurer = new ObjectFieldConfigurer(objectClass, "name");
        ObjectField objectField = objectFieldConfigurer.getObjectField();

        Assert.assertNotNull(objectField);
    }

}
