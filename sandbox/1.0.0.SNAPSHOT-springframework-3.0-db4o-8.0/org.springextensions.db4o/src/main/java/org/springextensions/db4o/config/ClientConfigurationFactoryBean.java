package org.springextensions.db4o.config;

import com.db4o.cs.Db4oClientServer;
import com.db4o.cs.config.ClientConfiguration;

/**
 * @author olli
 */
public class ClientConfigurationFactoryBean { // implements FactoryBean<ClientConfiguration> { https://jira.springframework.org/browse/OSGI-808

    private ClientConfiguration configuration = Db4oClientServer.newClientConfiguration();

    private CommonConfigurer commonConfigurer = new CommonConfigurer(configuration.common());

    private NetworkingConfigurer networkingConfigurer = new NetworkingConfigurer(configuration.networking());

    public ClientConfigurationFactoryBean() {
    }

    public ClientConfiguration getConfiguration() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @throws Exception
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public ClientConfiguration getObject() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<ClientConfiguration> getObjectType() {
        return ClientConfiguration.class;
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
     * @see com.db4o.cs.config.ClientConfiguration#common()
     */
    public CommonConfigurer getCommon() {
        return commonConfigurer;
    }

    /**
     * @return
     * @see com.db4o.cs.config.ClientConfiguration#networking()
     */
    public NetworkingConfigurer getNetworking() {
        return networkingConfigurer;
    }

    /* TODO
    public void addConfigurationItem(ClientConfigurationItem clientConfigurationItem) {
        configuration.addConfigurationItem(clientConfigurationItem);
    }
    */

}
