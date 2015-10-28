/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

import java.net.ServerSocket;

/**
 * Interface which describes the socket context in use.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public interface ISocketContext {

	/**
	 * Sets the port number to be used.
	 * 
	 * This function will automatically generate a new server socket and can only be called if the embedded 
	 * Mongo DB server is not already started.
	 * 
	 * @param port the port number to be used, set it to 0 to use a random free server port.
	 * 
	 * @throws ContextException If port setting has failed or is forbidden.
	 */
	public void setPort(int port) throws ContextException;
	
	/**
	 * Gets the server socket in use.
	 * 
	 * @return The server socket in use.
	 */
	public ServerSocket getServerSocket();
}
