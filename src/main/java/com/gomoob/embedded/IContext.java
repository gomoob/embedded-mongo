/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

/**
 * An object which describes the execution context.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public interface IContext {

	/**
	 * Gets the embedded Mongo DB execution context.
	 * 
	 * @return the embedded Mongo DB execution context.
	 */
	public IMongoContext getMongoContext();

	/**
	 * Gets the socket context.
	 * 
	 * @return the socket context.
	 */
	public ISocketContext getSocketContext();

}
