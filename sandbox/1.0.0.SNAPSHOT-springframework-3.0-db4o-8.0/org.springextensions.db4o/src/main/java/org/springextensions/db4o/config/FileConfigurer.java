package org.springextensions.db4o.config;

import java.io.IOException;

import com.db4o.config.ConfigScope;
import com.db4o.config.FileConfiguration;
import com.db4o.config.FreespaceConfiguration;
import com.db4o.config.GlobalOnlyConfigException;
import com.db4o.ext.DatabaseReadOnlyException;
import com.db4o.foundation.NotSupportedException;
import com.db4o.io.Storage;

/**
 * @author olli
 */
public class FileConfigurer {

    protected FileConfiguration fileConfiguration;

    public FileConfigurer(FileConfiguration fileConfiguration) {
        this.fileConfiguration = fileConfiguration;
    }

    /**
     * @param asynchronousSync
     * @see com.db4o.config.FileConfiguration#asynchronousSync(boolean)
     */
    public void setAsynchronousSync(boolean asynchronousSync) {
        fileConfiguration.asynchronousSync(asynchronousSync);
    }

    /**
     * @param blockSize
     * @see com.db4o.config.FileConfiguration#blockSize(int)
     */
    public void setBlockSize(int blockSize) {
        fileConfiguration.blockSize(blockSize);
    }

    /**
     * @param databaseGrowthSize
     * @see com.db4o.config.FileConfiguration#databaseGrowthSize(int)
     */
    public void setDatabaseGrowthSize(int databaseGrowthSize) {
        fileConfiguration.databaseGrowthSize(databaseGrowthSize);
    }

    // TODO
    // @see com.db4o.config.FileConfiguration#disableCommitRecovery
    // disableCommitRecovery();

    /**
     * @return
     * @see com.db4o.config.FileConfiguration#freespace()
     */
    public FreespaceConfiguration freespace() {
        return fileConfiguration.freespace();
    }

    public void setGenerateUUIDs(ConfigScope configScope) {
        // TODO
        throw new UnsupportedOperationException();
    }

    /**
     * @return
     * @see com.db4o.config.FileConfiguration#generateCommitTimestamps(boolean)
     */
    public void setGenerateCommitTimestamps(boolean flag) {
        fileConfiguration.generateCommitTimestamps(flag);
    }

    /**
     * @param storage
     * @throws GlobalOnlyConfigException
     * @see com.db4o.config.FileConfiguration#storage(com.db4o.io.Storage)
     */
    public void setStorage(Storage storage) throws GlobalOnlyConfigException {
        fileConfiguration.storage(storage);
    }

    /**
     * @param lockDatabaseFile
     * @see com.db4o.config.FileConfiguration#lockDatabaseFile(boolean)
     */
    public void setLockDatabaseFile(boolean lockDatabaseFile) {
        fileConfiguration.lockDatabaseFile(lockDatabaseFile);
    }

    /**
     * @param reserveStorageSpace
     * @throws DatabaseReadOnlyException
     * @throws NotSupportedException
     * @see com.db4o.config.FileConfiguration#reserveStorageSpace(long)
     */
    public void setReserveStorageSpace(long reserveStorageSpace) throws DatabaseReadOnlyException, NotSupportedException {
        fileConfiguration.reserveStorageSpace(reserveStorageSpace);
    }

    /**
     * @param blobPath
     * @throws IOException
     * @see com.db4o.config.FileConfiguration#blobPath(String)
     */
    public void setBlobPath(String blobPath) throws IOException {
        fileConfiguration.blobPath(blobPath);
    }

    /**
     * @param readOnly
     * @see com.db4o.config.FileConfiguration#readOnly(boolean)
     */
    public void setReadOnly(boolean readOnly) {
        fileConfiguration.readOnly(readOnly);
    }

    /**
     * @param recoveryMode
     */
    public void setRecoveryMode(boolean recoveryMode) {
        fileConfiguration.recoveryMode(recoveryMode);
    }

}
