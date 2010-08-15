package org.springextensions.db4o.config;

import com.db4o.config.IdSystemConfiguration;
import com.db4o.config.IdSystemFactory;

/**
 * @author olli
 */
public class IdSystemConfigurer {

    protected IdSystemConfiguration idSystemConfiguration;

    public enum System {

        InMemory,
        PointerBased,
        SingleBTree,
        StackedBTree
    }

    public IdSystemConfigurer(IdSystemConfiguration idSystemConfiguration) {
        this.idSystemConfiguration = idSystemConfiguration;
    }

    /**
     * @param factory
     * @see com.db4o.config.IdSystemConfiguration#useCustomSystem(com.db4o.config.IdSystemFactory)
     */
    public void setCustomSystem(IdSystemFactory factory) {
        idSystemConfiguration.useCustomSystem(factory);
    }

    /**
     * @param system
     * @see com.db4o.config.IdSystemConfiguration#useInMemorySystem()
     * @see com.db4o.config.IdSystemConfiguration#usePointerBasedSystem()
     * @see com.db4o.config.IdSystemConfiguration#useSingleBTreeSystem()
     * @see com.db4o.config.IdSystemConfiguration#useStackedBTreeSystem()
     */
    public void setSystem(System system) {
        switch (system) {
            case InMemory:
                idSystemConfiguration.useInMemorySystem();
                break;
            case PointerBased:
                idSystemConfiguration.usePointerBasedSystem();
                break;
            case SingleBTree:
                idSystemConfiguration.useSingleBTreeSystem();
                break;
            case StackedBTree:
                idSystemConfiguration.useStackedBTreeSystem();
                break;
        }
    }

}
