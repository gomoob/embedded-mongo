/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.context;

import com.gomoob.embedded.ContextException;
import com.gomoob.embedded.IContext;
import com.gomoob.embedded.IMongoContext;
import com.gomoob.embedded.ISocketContext;
import com.gomoob.embedded.State;

/**
 * Class which describes a command execution context.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class Context implements IContext {

	/**
	 * The embedded Mongo DB execution context.
	 */
	private IMongoContext mongoContext = null;
	
	/**
	 * The socket execution context
	 */
	private ISocketContext socketContext = null;

	/**
	 * The current server status.
	 */
	private State status = State.NEVER_STARTED;
	
	/**
	 * Creates a new execution context.
	 * 
	 * @throws ContextException If an error occured while creating the execution context.
	 */
	public Context() throws ContextException
	{
		this.mongoContext = new MongoContext();
		this.socketContext = new SocketContext();
	}
	
	/**
	 * {@inheritDoc}
	 */
	public IMongoContext getMongoContext() {
		return this.mongoContext;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public ISocketContext getSocketContext() {
		return this.socketContext;
	}

	/**
	 * {@inheritDoc}
	 */
	public State getState() {
		return this.status;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setState(State status) {
		this.status = status;
	}
}
