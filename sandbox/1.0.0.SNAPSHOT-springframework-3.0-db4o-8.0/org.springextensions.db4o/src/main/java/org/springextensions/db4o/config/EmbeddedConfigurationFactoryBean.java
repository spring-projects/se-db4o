package org.springextensions.db4o.config;

import com.db4o.Db4oEmbedded;
import com.db4o.config.EmbeddedConfiguration;

/**
 * @author olli
 */
public class EmbeddedConfigurationFactoryBean { // implements FactoryBean<EmbeddedConfiguration> { https://jira.springframework.org/browse/OSGI-808

    private EmbeddedConfiguration configuration = Db4oEmbedded.newConfiguration();

    private CommonConfigurer commonConfigurer = new CommonConfigurer(configuration.common());

    private FileConfigurer fileConfigurer = new FileConfigurer(configuration.file());

    private IdSystemConfigurer idSystemConfigurer = new IdSystemConfigurer(configuration.idSystem());

    public EmbeddedConfigurationFactoryBean() {
    }

    public EmbeddedConfiguration getConfiguration() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @throws Exception
     * @see org.springframework.beans.factory.FactoryBean#getObject()
     */
    public EmbeddedConfiguration getObject() throws Exception {
        return configuration;
    }

    /**
     * @return
     * @see org.springframework.beans.factory.FactoryBean#getObjectType()
     */
    public Class<EmbeddedConfiguration> getObjectType() {
        return EmbeddedConfiguration.class;
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
     * @see com.db4o.config.EmbeddedConfiguration#common()
     */
    public CommonConfigurer getCommon() {
        return commonConfigurer;
    }

    /**
     * @return
     * @see com.db4o.config.EmbeddedConfiguration#file()
     */
    public FileConfigurer getFile() {
        return fileConfigurer;
    }

    /**
     * @return
     * @see com.db4o.config.EmbeddedConfiguration#idSystem()
     */
    public IdSystemConfigurer getIdSystem() {
        return idSystemConfigurer;
    }

    /* TODO
    public void addConfigurationItem(EmbeddedConfigurationItem embeddedConfigurationItem) {
        configuration.addConfigurationItem(embeddedConfigurationItem);
    }
    */

}
