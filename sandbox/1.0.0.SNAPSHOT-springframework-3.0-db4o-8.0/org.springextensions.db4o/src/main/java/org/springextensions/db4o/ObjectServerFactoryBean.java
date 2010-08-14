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

import java.io.IOException;
import java.util.Iterator;
import java.util.Map.Entry;
import java.util.Properties;

import com.db4o.Db4o;
import com.db4o.ObjectServer;
import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.core.io.Resource;
import org.springframework.util.ObjectUtils;

/**
 * FactoryBean for creating {@link com.db4o.ObjectServer}s. This class adds support for
 * configuring user access through the userAccess property which takes a
 * Properties object with key the user name and value the password.
 * <p/>
 * <p/> Accepts a {@link com.db4o.cs.config.ServerConfiguration} object for local configurations. If none
 * is given, the global db4o configuration will be used.
 *
 * @author Costin Leau
 * @see com.db4o.Db4o
 */
public class ObjectServerFactoryBean implements FactoryBean<ObjectServer>, InitializingBean, DisposableBean {

    private static final Log log = LogFactory.getLog(ObjectServerFactoryBean.class);

    private Properties userAccess;

    private ObjectServer server;

    private Resource databaseFile;

    private ServerConfiguration serverConfiguration;

    private int port;

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ObjectServer getObject() throws Exception {
        return server;
    }

    /**
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<? extends ObjectServer> getObjectType() {
        return (server != null ? server.getClass() : ObjectServer.class);
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
        if (databaseFile == null) {
            throw new IllegalArgumentException("databaseFile is required");
        }
        if (port < 0) {
            throw new IllegalArgumentException("port must be greater then or equal to 0");
        }

        log.info("Database file is " + databaseFile.getFile().getAbsolutePath());

        // initialize the configuration to use only one method variant
        if (serverConfiguration == null) {
            serverConfiguration = Db4oClientServer.newServerConfiguration();
        }

        server = Db4oClientServer.openServer(serverConfiguration, databaseFile.getFile().getAbsolutePath(), port);

        log.info(Db4o.version());
        log.info("opened db4o server @" + ObjectUtils.getIdentityHexString(server));

        if (userAccess != null) {
            boolean debug = log.isDebugEnabled();
            for (Iterator iter = userAccess.entrySet().iterator(); iter.hasNext();) {
                Entry entry = (Entry) iter.next();
                server.grantAccess((String) entry.getKey(), (String) entry.getValue());
                if (debug)
                    log.debug("grated access to user `" + entry.getKey() + "` with password `"
                            + ObjectServerUtils.maskString(((String) entry.getValue())) + "`");
            }
        }
    }

    /**
     * @see org.springframework.beans.factory.DisposableBean#destroy()
     */
    public void destroy() throws Exception {
        log.info("closing object server @" + ObjectUtils.getIdentityHexString(server));
        server.close();
    }

    /**
     * @param userAccess The userAccess to set.
     */
    public void setUserAccess(Properties userAccess) {
        this.userAccess = userAccess;
    }

    public void setUserAccessLocation(Resource userAccess) {
        this.userAccess = new Properties();
        try {
            this.userAccess.load(userAccess.getInputStream());
        }
        catch (IOException e) {
            throw new BeanInitializationException("can't find resource", e);
        }
    }

    /**
     * @return Returns the databaseFile.
     */
    public Resource getDatabaseFile() {
        return databaseFile;
    }

    /**
     * @param databaseFile The databaseFile to set.
     */
    public void setDatabaseFile(Resource databaseFile) {
        this.databaseFile = databaseFile;
    }

    /**
     * @return Returns the port.
     */
    public int getPort() {
        return port;
    }

    /**
     * @param port The port to set.
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Set the configuration object to be used when creating the server. If none
     * is specified, the global db4o configuration is used.
     *
     * @param serverConfiguration The configuration to set.
     */
    public void setServerConfiguration(ServerConfiguration serverConfiguration) {
        this.serverConfiguration = serverConfiguration;
    }

}
