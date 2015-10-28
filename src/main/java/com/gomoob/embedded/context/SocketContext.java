/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.context;

import java.io.IOException;
import java.net.ServerSocket;

import com.gomoob.embedded.ContextException;
import com.gomoob.embedded.ISocketContext;

/**
 * Class which describe the socket execution context.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class SocketContext implements ISocketContext {

	/**
	 * The server socket in use.
	 */
	private ServerSocket serverSocket = null;

	/**
	 * Creates a new socket execution context.
	 * 
	 * @throws ContextException If an error occured while creating the socket context.
	 */
	public SocketContext() throws ContextException {
		
		this.setPort(0);
		
	}

	/**
	 * {@inheritDoc}
	 */
	public ServerSocket getServerSocket() {
		return this.serverSocket;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setPort(int port) throws ContextException {

		try {
			this.serverSocket = new ServerSocket(port);
		} catch (IOException ioex) {
			throw new ContextException(ioex);
		}

	}

}
