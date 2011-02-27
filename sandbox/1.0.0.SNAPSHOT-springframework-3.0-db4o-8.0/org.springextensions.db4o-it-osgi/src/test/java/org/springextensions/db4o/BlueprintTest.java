package org.springextensions.db4o;

import com.db4o.events.EventRegistry;
import org.apache.karaf.testing.AbstractIntegrationTest;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.ops4j.pax.exam.junit.MavenConfiguredJUnit4TestRunner;

/**
 * @author olli
 */
@RunWith(MavenConfiguredJUnit4TestRunner.class)
public class BlueprintTest extends AbstractIntegrationTest {

    @Test
    public void testDb4oOperations() {
        Db4oOperations db4oOperations = getOsgiService(Db4oOperations.class);
        Assert.assertNotNull("Db4oOperations is null", db4oOperations);
    }

    @Test
    public void testEventRegistry() {
        EventRegistry eventRegistry = getOsgiService(EventRegistry.class);
        Assert.assertNotNull("EventRegistry is null", eventRegistry);
    }

}
