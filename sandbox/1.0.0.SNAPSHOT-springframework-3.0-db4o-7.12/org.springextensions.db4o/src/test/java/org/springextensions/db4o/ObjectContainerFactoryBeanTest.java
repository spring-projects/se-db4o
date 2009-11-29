package org.springextensions.db4o;

import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.ext.ExtObjectContainer;
import com.db4o.io.PagingMemoryStorage;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * ObjectContainer FactoryBean tests.
 *
 * @author Costin Leau
 */
public class ObjectContainerFactoryBeanTest {

    private ObjectContainerFactoryBean factoryBean;

    @BeforeMethod
    public void setUp() throws Exception {
        EmbeddedConfiguration embeddedConfiguration = Db4oEmbedded.newConfiguration();
        embeddedConfiguration.file().storage(new PagingMemoryStorage());
        factoryBean = new ObjectContainerFactoryBean();
        factoryBean.setEmbeddedConfiguration(embeddedConfiguration);
        // factoryBean.setMemoryFile(new MemoryFile());
        factoryBean.afterPropertiesSet();
    }

    @AfterTest
    public void tearDown() throws Exception {
        factoryBean.destroy();
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.getObjectType()'
      */
    @Test
    public void testGetObjectType() {
        AssertJUnit.assertTrue(ObjectContainer.class.isAssignableFrom(factoryBean.getObjectType()));
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.isSingleton()'
      */
    @Test
    public void testIsSingleton() {
        AssertJUnit.assertTrue(factoryBean.isSingleton());
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.afterPropertiesSet()'
      */
    /*
    @Test
    public void testAfterPropertiesSet() throws Exception {
        factoryBean.afterPropertiesSet();
        try {
            // TODO: factoryBean.setMemoryFile(null);
            factoryBean.afterPropertiesSet();
            AssertJUnit.fail("expected illegal argument exception");
        } catch (IllegalArgumentException iae) {
            // it's okay
        }
    }
    */

    /*
      * Test method for 'org.springextensions.db4o.ObjectContainerFactoryBean.destroy()'
      */
    @Test
    public void testDestroy() throws Exception {
        AssertJUnit.assertFalse(((ExtObjectContainer) factoryBean.getObject()).isClosed());
        factoryBean.destroy();
        AssertJUnit.assertTrue(((ExtObjectContainer) factoryBean.getObject()).isClosed());
    }

}
