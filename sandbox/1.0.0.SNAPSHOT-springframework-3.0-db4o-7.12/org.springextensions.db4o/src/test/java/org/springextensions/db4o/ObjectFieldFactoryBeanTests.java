/*
 * Copyright 2002-2005 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.springextensions.db4o;

import com.db4o.config.ObjectClass;
import com.db4o.config.ObjectField;
import org.easymock.MockControl;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Unit tests to ensure the {@link org.springextensions.db4o.ObjectFieldFactoryBean} works correctly.
 * <p/>
 * Only way to verify that the explicit config is used is by verifying
 * the relevant methods on ObjectField are called.
 *
 * @since 0.9
 */
public class ObjectFieldFactoryBeanTests {

    private MockControl objectClassControl;

    private ObjectClass objectClass;

    private MockControl objectFieldControl;

    private ObjectField objectField;

    private ObjectFieldFactoryBean offb;

    /**
     * {@inheritDoc}
     * <p/>
     * Set up an {@link #objectClassControl} and {@link #objectFieldControl} and
     * associated proxies.
     * <p/>
     * By Default, the constructed {@link #offb} will use the
     * {@link #objectClass} which will return the {@link #objectField}.
     * <p/>
     * If test methods do not utilise these controls, they must explicitly set
     * them to be <code>null</code> otherwise {@link #tearDown()} will throw
     * an exception.
     */
    @BeforeMethod
    public void setUp() throws Exception {
        this.objectClassControl = MockControl.createNiceControl(ObjectClass.class);
        this.objectClass = (ObjectClass) objectClassControl.getMock();
        this.objectFieldControl = MockControl.createNiceControl(ObjectField.class);
        this.objectField = (ObjectField) objectFieldControl.getMock();

        String fieldName = "fieldName";
        this.objectClass.objectField(fieldName);
        this.objectClassControl.expectAndReturn(null, this.objectField);
        this.objectClassControl.replay();

        this.offb = new ObjectFieldFactoryBean();
        this.offb.setObjectClass(this.objectClass);
        this.offb.setFieldName(fieldName);
    }

    /**
     * {@inheritDoc}
     * <p/>
     * Verify the mocks have behaved as expected.
     */
    @AfterMethod
    public void tearDown() {
        if (this.objectClassControl != null) {
            this.objectClassControl.verify();
        }

        if (this.objectFieldControl != null) {
            this.objectFieldControl.verify();
        }
    }

    @Test
    public void testMissingObjectClass() throws Exception {
        // make sure that EasyMock doesn't verify unused mocks
        this.objectClassControl = null;
        this.objectFieldControl = null;

        // recreate the configuration because the ObjectClass has already been set.
        this.offb = new ObjectFieldFactoryBean();

        try {
            this.offb.afterPropertiesSet();
            AssertJUnit.fail("Expected IllegalArgumentException because the objectClass hasn't been set.");
        } catch (IllegalArgumentException e) {
            // fine, we expected this.
        }

        // now configure it
        this.offb.setObjectClass(this.objectClass);
        this.offb.afterPropertiesSet();

    }

    @Test
    public void testExplicitConfigurationIsUsed() throws Exception {
        // make sure that EasyMock doesn't verify unused mocks
        this.objectFieldControl = null;

        /**
         * This is actually tested in pretty much every other method, but it is
         * here for completeness.
         *
         */

        // construct the objectClass
        offb.afterPropertiesSet();
    }

    @Test
    public void testGetObjectType() throws Exception {
        // turn off EasyMock verification
        this.objectFieldControl = null;

        this.offb.afterPropertiesSet();

        AssertJUnit.assertEquals("factory class is wrong", this.objectField.getClass(), this.offb.getObjectType());
    }

    @Test
    public void testSingleton() throws Exception {
        // turn off EasyMock verification
        this.objectFieldControl = null;
        this.objectClassControl = null;

        this.offb.afterPropertiesSet();
        AssertJUnit.assertFalse("Shouldn't be a singleton", this.offb.isSingleton());

    }

    @Test
    public void testGetObject() throws Exception {
        // turn off EasyMock verification
        this.objectFieldControl = null;

        this.offb.afterPropertiesSet();

        // construct an empty objectClass. Just make sure it actually works.
        Object object = this.offb.getObject();
        AssertJUnit.assertNotNull("expected at least a not null object", object);
        AssertJUnit.assertTrue("object is of wrong type", ObjectField.class.isAssignableFrom(object.getClass()));
    }

    @Test
    public void testDefaultConfigurationIsUsed() throws Exception {
        // err, no way to test this. Could use AOP to intercept the DB4o.config
        // method...

        // bypass EasyMock verification.
        this.objectClassControl = null;
        this.objectFieldControl = null;
    }

    @Test
    public void testCascadeOnActivate() throws Exception {
        Boolean value = Boolean.TRUE;
        this.objectField.cascadeOnActivate(value.booleanValue());
        this.objectFieldControl.replay();

        this.offb.setCascadeOnActivate(value);
        this.offb.afterPropertiesSet();
    }

    @Test
    public void testCascadeOnDelete() throws Exception {
        Boolean value = Boolean.TRUE;
        this.objectField.cascadeOnDelete(value.booleanValue());
        this.objectFieldControl.replay();

        this.offb.setCascadeOnDelete(value);
        this.offb.afterPropertiesSet();
    }

    @Test
    public void testCascadeOnUpdate() throws Exception {
        Boolean value = Boolean.TRUE;
        this.objectField.cascadeOnUpdate(value.booleanValue());
        this.objectFieldControl.replay();

        this.offb.setCascadeOnUpdate(value);
        this.offb.afterPropertiesSet();
    }


    @Test
    public void testIndexed() throws Exception {
        Boolean value = Boolean.TRUE;
        this.objectField.indexed(value.booleanValue());
        this.objectFieldControl.replay();

        this.offb.setIndexed(value);
        this.offb.afterPropertiesSet();
    }

    @Test
    public void testRenameValue() throws Exception {
        String value = "renamed value";
        this.objectField.rename(value);
        this.objectFieldControl.replay();

        this.offb.setRenameValue(value);
        this.offb.afterPropertiesSet();
    }

}
