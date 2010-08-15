package org.springextensions.db4o.config;

import com.db4o.cs.config.ClientServerFactory;
import com.db4o.cs.config.NetworkingConfiguration;
import com.db4o.cs.foundation.Socket4Factory;
import com.db4o.messaging.MessageRecipient;

/**
 * @author olli
 */
public class NetworkingConfigurer {

    protected NetworkingConfiguration networkingConfiguration;

    public NetworkingConfigurer(NetworkingConfiguration networkingConfiguration) {
        this.networkingConfiguration = networkingConfiguration;
    }

    /**
     * @param flag
     * @see com.db4o.cs.config.NetworkingConfiguration#batchMessages(boolean)
     */
    public void setBatchMessages(boolean flag) {
        networkingConfiguration.batchMessages(flag);
    }

    /**
     *
     * @param clientServerFactory
     * @see com.db4o.cs.config.NetworkingConfiguration#clientServerFactory(com.db4o.cs.config.ClientServerFactory)
     */
    public void setClientServerFactory(ClientServerFactory clientServerFactory) {
        networkingConfiguration.clientServerFactory(clientServerFactory);
    }

    /**
     *
     * @param maxSize
     * @see com.db4o.cs.config.NetworkingConfiguration#maxBatchQueueSize(int)
     */
    public void setMaxBatchQueueSize(int maxSize) {
        networkingConfiguration.maxBatchQueueSize(maxSize);
    }

    /**
     *
     * @param messageRecipient
     * @see com.db4o.cs.config.NetworkingConfiguration#messageRecipient(com.db4o.messaging.MessageRecipient)
     */
    public void setMessageRecipient(MessageRecipient messageRecipient) {
        networkingConfiguration.messageRecipient(messageRecipient);
    }

    /**
     * @param flag
     * @see com.db4o.cs.config.NetworkingConfiguration#singleThreadedClient(boolean)
     */
    public void setSingleThreadedClient(boolean flag) {
        networkingConfiguration.singleThreadedClient(flag);
    }

    /**
     * @param socket4Factory
     * @see com.db4o.cs.config.NetworkingConfiguration#socketFactory(com.db4o.cs.foundation.Socket4Factory)
     */
    public void setSocketFactory(Socket4Factory socket4Factory) {
        networkingConfiguration.socketFactory(socket4Factory);
    }

}
