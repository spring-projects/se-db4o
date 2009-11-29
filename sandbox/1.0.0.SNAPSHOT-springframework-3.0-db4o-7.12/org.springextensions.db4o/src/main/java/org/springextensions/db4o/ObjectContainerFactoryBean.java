/**
 * Created on Nov 5, 2005
 *
 * $Id$
 * $Revision$
 */
package org.springextensions.db4o;

import java.io.IOException;

import com.db4o.Db4o;
import com.db4o.Db4oEmbedded;
import com.db4o.ObjectContainer;
import com.db4o.ObjectServer;
import com.db4o.config.Configuration;
import com.db4o.config.EmbeddedConfiguration;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

/**
 * Class used for creating an {@link ObjectContainer} in a singleton manner as
 * these are thread-safe.
 * <p/>
 * <p/> The factory bean will try to create using the available properties in
 * the following order:
 * <ol>
 * <li>if databaseFile is set, a local file based client will be created.</li>
 * <li>if memory file is set, a local memory based client will be created.
 * <li>
 * <li>if server is set, a client within the same VM as the server will be
 * created.</li>
 * <li>if all the above fail the client will be opened using hostName, port,
 * user, password to a remote server.</li>
 * </ol>
 * <p/>
 * <p/>
 * <p/> Accepts a {@link Configuration} object for local configurations. If none
 * is given, the global db4o configuration will be used.
 *
 * @author Costin Leau
 * @see com.db4o.Db4o
 */
public class ObjectContainerFactoryBean implements FactoryBean<ObjectContainer>, InitializingBean, DisposableBean {

    private static final Log log = LogFactory.getLog(ObjectContainerFactoryBean.class);

    private ObjectContainer container;

    // Local mode

    // file based
    private Resource databaseFile;

    // memory based
    // TODO: Storage

    // Server mode

    // local server mode
    private ObjectServer server;

    // remote server mode
    private String hostname;

    private String username;

    private String password;

    private int port;

    private EmbeddedConfiguration embeddedConfiguration;

    private ClientConfiguration clientConfiguration;

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ObjectContainer getObject() throws Exception {
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

    /**
     * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
     */
    public void afterPropertiesSet() throws Exception {
        if (databaseFile != null) {
            container = openEmbeddedContainer(embeddedConfiguration, databaseFile.getFile().getAbsolutePath());
            /*
        } else if (memoryFile != null) { // TODO: Storage
            container = ExtDb4o.openMemoryFile(configuration, memoryFile);
            log.info("opened db4o local memory-based objectContainer @" + ObjectUtils.getIdentityHexString(container));
            */
        } else if (embeddedConfiguration != null && embeddedConfiguration.file().storage() != null) {
            container = openEmbeddedContainer(embeddedConfiguration, "TESTING"); // TODO
        } else if (server != null) {
            container = openEmbeddedClient(server);
        } else if (hostname != null && port > 0 && username != null) {
            container = openRemoteClient(clientConfiguration, hostname, port, username, password);
        } else {
            throw new IllegalArgumentException("mandatory fields are not set; databaseFile or memoryFile or container or (hostName, port, user) are required");
        }
        log.info(Db4o.version());
    }

    protected ObjectContainer openEmbeddedContainer(EmbeddedConfiguration embeddedConfiguration, String filename) throws IOException {
        if (embeddedConfiguration == null) {
            embeddedConfiguration = Db4oEmbedded.newConfiguration();
        }
        ObjectContainer container = Db4oEmbedded.openFile(embeddedConfiguration, filename);
        log.info("opened db4o local file-based objectContainer @" + ObjectUtils.getIdentityHexString(container));
        return container;
    }

    protected ObjectContainer openEmbeddedClient(ObjectServer server) {
        ObjectContainer container = server.openClient();
        log.info("opened db4o embedded server-based objectContainer @" + ObjectUtils.getIdentityHexString(container));
        return container;
    }

    protected ObjectContainer openRemoteClient(ClientConfiguration clientConfiguration, String hostname, int port, String username, String password) {
        if (clientConfiguration == null) {
            clientConfiguration = Db4oClientServer.newClientConfiguration();
        }
        container = Db4oClientServer.openClient(clientConfiguration, hostname, port, username, password);
        log.info("opened db4o remote server-based objectContainer @" + ObjectUtils.getIdentityHexString(container));
        return container;
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {
        log.info("closing object container " + ObjectUtils.getIdentityHexString(container));
        container.close();
    }

    /**
     * @param file The file to set.
     */
    public void setDatabaseFile(Resource file) {
        this.databaseFile = file;
    }

    /**
     * @param hostname The hostname to set.
     */
    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    /**
     * @param password The password to set.
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * @param server The server to set.
     */
    public void setServer(ObjectServer server) {
        this.server = server;
    }

    /**
     * @param username The username to set.
     */
    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmbeddedConfiguration(EmbeddedConfiguration embeddedConfiguration) {
        this.embeddedConfiguration = embeddedConfiguration;
    }

    public void setClientConfiguration(ClientConfiguration clientConfiguration) {
        this.clientConfiguration = clientConfiguration;
    }

}
