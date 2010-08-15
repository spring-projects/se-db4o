package org.springextensions.db4o.config;

import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ServerConfiguration;

/**
 * @author olli
 */
public class ServerConfigurationFactoryBean { // implements FactoryBean<ServerConfiguration> { https://jira.springframework.org/browse/OSGI-808

    private ServerConfiguration configuration = Db4oClientServer.newServerConfiguration();

    private CommonConfigurer commonConfigurer = new CommonConfigurer(configuration.common());

    private NetworkingConfigurer networkingConfigurer = new NetworkingConfigurer(configuration.networking());

    private FileConfigurer fileConfigurer = new FileConfigurer(configuration.file());

    private IdSystemConfigurer idSystemConfigurer = new IdSystemConfigurer(configuration.idSystem());

    public ServerConfigurationFactoryBean() {
    }

    public ServerConfiguration getConfiguration() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @throws Exception
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ServerConfiguration getObject() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<ServerConfiguration> getObjectType() {
        return ServerConfiguration.class;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#isSingleton()
     */
    public boolean isSingleton() {
        return true;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#common()
     */
    public CommonConfigurer getCommon() {
        return commonConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#networking()
     */
    public NetworkingConfigurer getNetworking() {
        return networkingConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#file()
     */
    public FileConfigurer getFile() {
        return fileConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ServerConfiguration#idSystem()
     */
    public IdSystemConfigurer getIdSystem() {
        return idSystemConfigurer;
    }

    /**
     * @param milliseconds
     * @see com.db4o.cs.config.ServerConfiguration#timeoutServerSocket(int)
     */
    public void setTimeoutServerSocket(int milliseconds) {
        configuration.timeoutServerSocket(milliseconds);
    }

    /* TODO
    public void addConfigurationItem(ServerConfigurationItem serverConfigurationItem) {
        configuration.addConfigurationItem(serverConfigurationItem);
    }
    */

}
