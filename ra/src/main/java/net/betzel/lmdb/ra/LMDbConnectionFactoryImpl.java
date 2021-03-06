/*
    Copyright 2017 Maurice Betzel

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
 */
package net.betzel.lmdb.ra;

import javax.naming.NamingException;
import javax.naming.Reference;
import javax.resource.ResourceException;
import javax.resource.spi.ConnectionManager;
import java.util.List;
import java.util.logging.Logger;

/**
 * LMDbConnectionFactoryImpl
 *
 * @version $Revision: $
 */
public class LMDbConnectionFactoryImpl implements LMDbConnectionFactory {

    private static final long serialVersionUID = 1L;

    private static Logger log = Logger.getLogger(LMDbConnectionFactoryImpl.class.getName());

    /**
     * Reference
     */
    private Reference reference;

    /**
     * ManagedConnectionFactory
     */
    private LMDbManagedConnectionFactory managedConnectionFactory;

    /**
     * ConnectionManager
     */
    private ConnectionManager connectionManager;

    /**
     * Default constructor
     */
    public LMDbConnectionFactoryImpl() {

    }

    /**
     * Default constructor
     *
     * @param managedConnectionFactory       ManagedConnectionFactory
     * @param cxManager ConnectionManager
     */
    public LMDbConnectionFactoryImpl(LMDbManagedConnectionFactory managedConnectionFactory, ConnectionManager cxManager) {
        log.finest("LMDbConnectionFactoryImpl()");
        this.managedConnectionFactory = managedConnectionFactory;
        this.connectionManager = cxManager;
    }

    public int getDatabaseMaxKeySize() {
        return managedConnectionFactory.getDatabaseMaxKeySize();
    }

    public List<String> getDatabaseNames() {
        return managedConnectionFactory.getDatabaseNames();
    }

    /**
     * Get connection from factory
     *
     * @return LMDbConnection instance
     * @throws ResourceException Thrown if a connection can't be obtained
     */
    @Override
    public LMDbConnection getConnection(String databaseName) throws ResourceException {
        log.finest("getConnection()");
        LMDbConnectionRequestInfo connectionRequestInfo = new LMDbConnectionRequestInfoImpl( managedConnectionFactory.getFilePath(), databaseName);
        return (LMDbConnection) connectionManager.allocateConnection(managedConnectionFactory, connectionRequestInfo);
    }

    /**
     * Get the Reference instance.
     *
     * @return Reference instance
     * @throws NamingException Thrown if a reference can't be obtained
     */
    @Override
    public Reference getReference() throws NamingException {
        log.finest("getReference()");
        return reference;
    }

    /**
     * Set the Reference instance.
     *
     * @param reference A Reference instance
     */
    @Override
    public void setReference(Reference reference) {
        log.finest("setReference()");
        this.reference = reference;
    }

}