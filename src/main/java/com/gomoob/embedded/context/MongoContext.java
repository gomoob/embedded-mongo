/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.context;

import java.io.IOException;
import java.net.UnknownHostException;

import com.gomoob.embedded.ContextException;
import com.gomoob.embedded.IMongoContext;

import de.flapdoodle.embed.mongo.Command;
import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.config.DownloadConfigBuilder;
import de.flapdoodle.embed.mongo.config.ExtractedArtifactStoreBuilder;
import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.MongodConfigBuilder;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.mongo.config.RuntimeConfigBuilder;
import de.flapdoodle.embed.mongo.distribution.Version;
import de.flapdoodle.embed.process.config.IRuntimeConfig;
import de.flapdoodle.embed.process.config.store.IDownloadConfig;
import de.flapdoodle.embed.process.extract.UserTempNaming;
import de.flapdoodle.embed.process.runtime.Network;

/**
 * Class which describes the embedded Mongo DB server execution context.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class MongoContext implements IMongoContext {

	/**
	 * The embedded Mongo DB configuration in use.
	 */
	protected IMongodConfig mongodConfig = null;

	/**
	 * The runtime configuration is use.
	 */
	protected IRuntimeConfig runtimeConfig = null;
	
	/**
	 * A reference to the Mongo DB executable currently in execution. 
	 */
	protected MongodExecutable mongodExecutable = null;
	
	/**
	 * A reference to the Mongo DB process.
	 */
	protected MongodProcess mongodProcess = null;

	/**
	 * The network configuration is use.
	 */
	protected Net net = null;

	/**
	 * Creates a new embedded Mongo DB server execution context.
	 * 
	 * @throws ContextException
	 *             If an error occurs while creating the context.
	 */
	public MongoContext() throws ContextException {

		Command command = Command.MongoD;

		IDownloadConfig downloadConfig = new DownloadConfigBuilder()
				.defaultsForCommand(command).build();

		ExtractedArtifactStoreBuilder artifactStoreBuilder = new ExtractedArtifactStoreBuilder();
		artifactStoreBuilder.defaults(command);
		artifactStoreBuilder.download(downloadConfig);
		artifactStoreBuilder.executableNaming(new UserTempNaming());

		RuntimeConfigBuilder runtimeConfigBuilder = new RuntimeConfigBuilder();
		runtimeConfigBuilder.defaults(command);
		runtimeConfigBuilder.artifactStore(artifactStoreBuilder);

		this.runtimeConfig = runtimeConfigBuilder.build();

		// By default we use a random port (please note that this function will
		// re-generae a new net config)
		this.setPort(0);

		// Creates a default Mongod configuration
		this.createMongodConfig();

	}
	
	private void createMongodConfig() throws ContextException {
		
		MongodConfigBuilder mongodConfigBuilder = null; 

		try {
			mongodConfigBuilder = new MongodConfigBuilder();
		} catch (UnknownHostException uhex) {
			throw new ContextException(uhex);
		} catch (IOException ioex) {
			throw new ContextException(ioex);
		}

		mongodConfigBuilder.version(Version.Main.PRODUCTION);
		mongodConfigBuilder.net(this.net);
		
		this.mongodConfig = mongodConfigBuilder.build();
		
	}

	/**
	 * {@inheritDoc}
	 */
	public Net getNet() {
		return this.net;
	}

	/**
	 * {@inheritDoc}
	 */
	public IMongodConfig getMongodConfig() {
		return this.mongodConfig;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public MongodExecutable getMongodExecutable() {
		return this.mongodExecutable;
	}

	/**
	 * {@inheritDoc}
	 */
	public MongodProcess getMongodProcess() {
		return this.mongodProcess;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public IRuntimeConfig getRuntimeConfig() {
		return this.runtimeConfig;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setMongodExecutable(MongodExecutable mongodExecutable)
	{
		this.mongodExecutable = mongodExecutable;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setMongodProcess(MongodProcess mongodProcess)
	{
		this.mongodProcess = mongodProcess;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setPort(int port) throws ContextException {

		int portToUse = port;

		// If port is equal to 0 we pick a free port number
		if (port == 0) {

			try {

				portToUse = Network.getFreeServerPort();

			} catch (IOException ioex) {

				throw new ContextException(ioex);

			}

		}

		boolean ipv6 = false;

		try {

			ipv6 = Network.localhostIsIPv6();

		} catch (UnknownHostException uhex) {

			throw new ContextException(uhex);

		}

		this.net = new Net(portToUse, ipv6);

		// Re-creates the Mongod configuration
		this.createMongodConfig();
	}

}
