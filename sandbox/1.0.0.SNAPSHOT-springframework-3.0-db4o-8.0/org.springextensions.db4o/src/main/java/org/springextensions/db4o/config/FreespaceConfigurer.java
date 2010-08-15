package org.springextensions.db4o.config;

import com.db4o.config.FreespaceConfiguration;
import com.db4o.config.FreespaceFiller;

/**
 * @author olli
 */
public class FreespaceConfigurer {

    protected FreespaceConfiguration freespaceConfiguration;

    public enum System {
        BTree,
        Ram
    }

    public FreespaceConfigurer(FreespaceConfiguration freespaceConfiguration) {
        this.freespaceConfiguration = freespaceConfiguration;
    }

    /**
     * @param byteCount
     * @see com.db4o.config.FreespaceConfiguration#discardSmallerThan(int)
     */
    public void setDiscardSmallerThan(int byteCount) {
        freespaceConfiguration.discardSmallerThan(byteCount);
    }

    /**
     * @param freespaceFiller
     * @see com.db4o.config.FreespaceConfiguration#freespaceFiller(com.db4o.config.FreespaceFiller)
     */
    public void setFreespaceFiller(FreespaceFiller freespaceFiller) {
        freespaceConfiguration.freespaceFiller(freespaceFiller);
    }

    /**
     * @param system
     * @see com.db4o.config.FreespaceConfiguration#useBTreeSystem()
     * @see com.db4o.config.FreespaceConfiguration#useRamSystem()
     */
    public void setSystem(System system) {
        switch (system) {
            case BTree:
                freespaceConfiguration.useBTreeSystem();
                break;
            case Ram:
                freespaceConfiguration.useRamSystem();
                break;
        }
    }

}
