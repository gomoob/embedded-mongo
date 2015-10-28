/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

import de.flapdoodle.embed.mongo.config.IMongodConfig;
import de.flapdoodle.embed.mongo.config.Net;
import de.flapdoodle.embed.process.config.IRuntimeConfig;

/**
 * Interface which describe the embedded Mongo DB server in use.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public interface IMongoContext {

	/**
	 * Gets the network configuration in use.
	 * 
	 * @return The network configuration in use.
	 */
	public Net getNet();
	
	/**
	 * Gets the embedded Mongo DB configuration in use.
	 * 
	 * @return the embedded Mongo DB configuration in use.
	 */
	public IMongodConfig getMongodConfig();

	/**
	 * Gets the runtime configuration in use.
	 * 
	 * @return the runtime configuration in use.
	 */
	public IRuntimeConfig getRuntimeConfig();
	
	/**
	 * Sets the port number to be used.
	 * 
	 * This function will automatically generate a new network configuration and can only be called if the embedded 
	 * Mongo DB server is not already started.
	 * 
	 * @param port the port number to be used, set it to 0 to use a random free server port.
	 * 
	 * @throws ContextException If port setting has failed or is forbidden.
	 */
	public void setPort(int port) throws ContextException;

}
