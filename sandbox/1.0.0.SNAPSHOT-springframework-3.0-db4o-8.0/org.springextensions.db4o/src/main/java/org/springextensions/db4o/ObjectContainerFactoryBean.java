/*
 * Copyright 2005-2010 the original author or authors.
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

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.FactoryBeanNotInitializedException;
import org.springframework.util.ObjectUtils;

/**
 * <p>example <b>OSGi Blueprint</b> configuration for embedded in-memory database:</p>
 * <pre>
 * &lt;blueprint xmlns=&quot;http://www.osgi.org/xmlns/blueprint/v1.0.0&quot;&gt;
 *
 *   &lt;bean id=&quot;embeddedConfiguration&quot; class=&quot;org.springextensions.db4o.config.EmbeddedConfigurationFactoryBean&quot;&gt;
 *     &lt;property name=&quot;file.storage&quot;&gt;
 *       &lt;bean class=&quot;com.db4o.io.PagingMemoryStorage&quot;/&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;bean id=&quot;objectContainerFactory&quot; class=&quot;org.springextensions.db4o.ObjectContainerFactoryBean&quot; init-method=&quot;initialize&quot; destroy-method=&quot;destroy&quot;&gt;
 *     &lt;property name=&quot;name&quot; value=&quot;memory&quot;/&gt;
 *     &lt;property name=&quot;embeddedConfiguration&quot;&gt;
 *       &lt;bean factory-ref=&quot;embeddedConfiguration&quot; factory-method=&quot;getObject&quot;/&gt;
 *     &lt;/property&gt;
 *   &lt;/bean&gt;
 *
 *   &lt;bean id=&quot;objectContainer&quot; factory-ref=&quot;objectContainerFactory&quot; factory-method=&quot;getObject&quot;/&gt;
 *
 *   &lt;bean id=&quot;db4oTemplate&quot; class=&quot;org.springextensions.db4o.Db4oTemplate&quot;&gt;
 *     &lt;argument ref=&quot;objectContainer&quot;/&gt;
 *   &lt;/bean&gt;
 *
 * &lt;/blueprint&gt;
 * </pre>
 *
 * @author Costin Leau
 * @author olli
 */
public class ObjectContainerFactoryBean { // implements FactoryBean<ObjectContainer> { https://jira.springframework.org/browse/OSGI-808

    private ObjectContainer container;

    /**
     * @see com.db4o.Db4oEmbedded#openFile(String)
     */
    private String name;

    /**
     * @see com.db4o.ObjectServer#openClient()
     */
    private ObjectServer server;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private String hostname;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private int port;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private String username;

    /**
     * @see com.db4o.cs.Db4oClientServer#openClient(String, int, String, String)
     */
    private String password;

    private EmbeddedConfiguration embeddedConfiguration;

    private ClientConfiguration clientConfiguration;

    private final Log log = LogFactory.getLog(ObjectContainerFactoryBean.class);

    public void setName(String name) {
        this.name = name;
    }

    public void setServer(ObjectServer server) {
        this.server = server;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setEmbeddedConfiguration(EmbeddedConfiguration embeddedConfiguration) {
        this.embeddedConfiguration = embeddedConfiguration;
    }

    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ObjectContainer getObject() throws Exception {
        if (container == null) {
            throw new FactoryBeanNotInitializedException("object container not opened");
        }
        return container;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<? extends ObjectContainer> getObjectType() {
        return (container != null ? container.getClass() : ObjectContainer.class);
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    @PostConstruct
    public void initialize() {
        if (name != null) {
            container = openEmbeddedContainer(embeddedConfiguration, name);
        } else if (server != null) {
            container = openEmbeddedClientContainer(server);
        } else if (hostname != null && port > 0 && username != null) {
            container = openRemoteClientContainer(clientConfiguration, hostname, port, username, password);
        } else {
            throw new IllegalArgumentException("mandatory fields are not set: database name or embedded database server or remote database server (hostname, port, username)");
        }
        log.info(Db4o.version());
    }

    @PreDestroy
    public void destroy() {
        log.info("closing object container " + ObjectUtils.getIdentityHexString(container));
        container.close();
    }

    protected ObjectContainer openEmbeddedContainer(EmbeddedConfiguration embeddedConfiguration, String name) {
        ObjectContainer container;
        if (embeddedConfiguration == null) {
            container = Db4oEmbedded.openFile(name);
        } else {
            log.info("using configuration: embedded");
            container = Db4oEmbedded.openFile(embeddedConfiguration, name);
        }
        log.info("embedded container opened: " + ObjectUtils.getIdentityHexString(container));
        return container;
    }

    protected ObjectContainer openEmbeddedClientContainer(ObjectServer server) {
        ObjectContainer container = server.openClient();
        log.info("embedded client container opened: " + ObjectUtils.getIdentityHexString(container));
        return container;
    }

    protected ObjectContainer openRemoteClientContainer(ClientConfiguration clientConfiguration, String hostname, int port, String username, String password) {
        ObjectContainer container;
        if (clientConfiguration == null) {
            container = Db4oClientServer.openClient(hostname, port, username, password);
        } else {
            log.info("using configuration: client");
            container = Db4oClientServer.openClient(clientConfiguration, hostname, port, username, password);
        }
        log.info("remote client container opened: " + ObjectUtils.getIdentityHexString(container));
        return container;
    }

}
