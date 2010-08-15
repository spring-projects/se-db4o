package org.springextensions.db4o.config;

import com.db4o.config.EnvironmentConfiguration;

/**
 * TODO
 *
 * @author olli
 */
public class EnvironmentConfigurer {

    protected EnvironmentConfiguration environmentConfiguration;

    public EnvironmentConfigurer(EnvironmentConfiguration environmentConfiguration) {
        this.environmentConfiguration = environmentConfiguration;
    }

}
