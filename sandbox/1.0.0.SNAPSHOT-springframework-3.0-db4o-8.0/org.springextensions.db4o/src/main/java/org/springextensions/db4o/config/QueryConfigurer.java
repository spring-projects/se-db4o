package org.springextensions.db4o.config;

import com.db4o.config.QueryConfiguration;

/**
 * TODO
 *
 * @author olli
 */
public class QueryConfigurer {

    protected QueryConfiguration queryConfiguration;

    public QueryConfigurer(QueryConfiguration queryConfiguration) {
        this.queryConfiguration = queryConfiguration;
    }

}
