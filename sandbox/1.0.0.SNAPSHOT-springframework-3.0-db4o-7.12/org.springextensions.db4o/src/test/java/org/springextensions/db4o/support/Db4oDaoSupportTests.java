package org.springextensions.db4o.support;

import com.db4o.ObjectContainer;
import org.easymock.MockControl;
import org.easymock.classextension.MockClassControl;
import org.springextensions.db4o.Db4oTemplate;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

public class Db4oDaoSupportTests {

    private MockControl containerControl;

    private MockControl templateControl;

    private ObjectContainer container;

    private Db4oTemplate template;

    @BeforeMethod
    public void setUp() throws Exception {
        containerControl = MockControl.createStrictControl(ObjectContainer.class);
        container = (ObjectContainer) containerControl.getMock();

        templateControl = MockClassControl.createStrictControl(Db4oTemplate.class);
        template = (Db4oTemplate) templateControl.getMock();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        containerControl.verify();
        templateControl.verify();
    }

    /*
      * Test method for
      * 'org.springextensions.db4o.support.Db4oDaoSupport.setObjectContainer'
      */
    @Test
    public void testConfigureWithObjectContainer() {
        containerControl.replay();
        templateControl.replay();

        Db4oDaoSupport dao = new Db4oDaoSupport() {
        };
        dao.setObjectContainer(container);
        // must not throw an exception
        dao.afterPropertiesSet();

        AssertJUnit.assertNotNull(dao.getDb4oTemplate());
        AssertJUnit.assertEquals(container, dao.getDb4oTemplate().getObjectContainer());
        AssertJUnit.assertEquals(container, dao.getObjectContainer());
    }

    /*
      * Test method for 'org.springextensions.db4o.support.Db4oDaoSupport.setDb4oTemplate'
      */
    @Test
    public void testConfigureWithTemplate() {
        templateControl.expectAndReturn(template.getObjectContainer(), container, 2);

        containerControl.replay();
        templateControl.replay();

        Db4oDaoSupport dao = new Db4oDaoSupport() {
        };
        dao.setDb4oTemplate(template);
        dao.afterPropertiesSet();

        AssertJUnit.assertEquals(template, dao.getDb4oTemplate());
        AssertJUnit.assertEquals(container, dao.getObjectContainer());
        AssertJUnit.assertEquals(container, dao.getDb4oTemplate().getObjectContainer());
    }

    /*
      * Test method for 'org.springextensions.db4o.support.Db4oDaoSupport.checkDaoConfig'
      */
    @Test
    public void testMissingConfiguration() {
        containerControl.replay();
        templateControl.replay();
        try {
            Db4oDaoSupport dao = new Db4oDaoSupport() {
            };
            dao.afterPropertiesSet();
            AssertJUnit.fail("Should have thrown IllegalArgumentException because neither #setObjectContainer nor #setTemplate have been invoked");
        }
        catch (IllegalArgumentException ex) {
            // it's ok. We expect this exception to be thrown
        }
    }

}
