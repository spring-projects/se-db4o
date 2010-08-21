package org.springextensions.db4o;

import org.springframework.test.context.ContextConfiguration;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * @author olli
 */
@ContextConfiguration
public class EmbeddedClientServerTest extends ObjectContainerTest {

    @Test
    public void testObjectContainer() {
        Assert.assertNotNull(objectContainer);
        Object object = new Object();
        objectContainer.store(object);
    }

}
