package org.springextensions.db4o.config;

import com.db4o.config.ObjectClass;
import com.db4o.io.PagingMemoryStorage;
import org.springextensions.db4o.example.Person;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * author: olli
 */
// TODO: write proper tests
public class ObjectClassConfigurerTest {

    public static final int MAXIMUM_ACTIVATION_DEPTH = 100;

    public static final int MINIMUM_ACTIVATION_DEPTH = 10;

    @Test
    public void testObjectClass() throws Exception {
        EmbeddedConfigurationFactoryBean embeddedConfigurationFactoryBean = new EmbeddedConfigurationFactoryBean();
        embeddedConfigurationFactoryBean.getFile().setStorage(new PagingMemoryStorage());

        ObjectClassConfigurer objectClassConfigurer = new ObjectClassConfigurer(embeddedConfigurationFactoryBean.getCommon(), Person.class);
        ObjectClass objectClass = objectClassConfigurer.getObjectClass();

        objectClassConfigurer.setMaximumActivationDepth(MAXIMUM_ACTIVATION_DEPTH);
        objectClassConfigurer.setMinimumActivationDepth(MINIMUM_ACTIVATION_DEPTH);

        Assert.assertNotNull(objectClass);

        // Assert.assertEquals(MAXIMUM_ACTIVATION_DEPTH, objectClass. ???);

        Assert.assertEquals(MINIMUM_ACTIVATION_DEPTH, objectClass.minimumActivationDepth());
    }

}
