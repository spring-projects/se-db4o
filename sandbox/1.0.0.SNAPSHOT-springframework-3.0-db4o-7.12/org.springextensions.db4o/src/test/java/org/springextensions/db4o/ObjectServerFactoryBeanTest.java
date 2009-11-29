/*
 * Copyright 2005-2009 the original author or authors.
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

import com.db4o.ObjectServer;
import org.springframework.core.io.ClassPathResource;
import org.testng.AssertJUnit;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * Test class for Object Server FactoryBean.
 *
 * @author Costin Leau
 */
public class ObjectServerFactoryBeanTest {

    private ObjectServerFactoryBean serverFB;

    @BeforeMethod
    public void setUp() throws Exception {
        serverFB = new ObjectServerFactoryBean();
        serverFB.setDatabaseFile(new ClassPathResource("testdb.file"));
        serverFB.setPort(0);
        serverFB.afterPropertiesSet();
    }

    @AfterMethod
    public void tearDown() throws Exception {
        serverFB.destroy();
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectServerFactoryBean.getObjectType()'
      */
    @Test
    public void testGetObjectType() {
        AssertJUnit.assertTrue(ObjectServer.class.isAssignableFrom(serverFB.getObjectType()));
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectServerFactoryBean.isSingleton()'
      */
    @Test
    public void testIsSingleton() {
        AssertJUnit.assertTrue(serverFB.isSingleton());
    }

    /*
      * Test method for 'org.springextensions.db4o.ObjectServerFactoryBean.afterPropertiesSet()'
      */
    @Test
    public void testAfterPropertiesSet() throws Exception {
        serverFB.setDatabaseFile(null);
        try {
            serverFB.afterPropertiesSet();
            AssertJUnit.fail("expected IllegalArgumentException");
        }
        catch (IllegalArgumentException iae) {
            // it's okay
        }

    }

}
