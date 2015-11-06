/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.command;

import com.gomoob.embedded.CommandException;
import com.gomoob.embedded.IContext;
import com.gomoob.embedded.IResponse;
import com.gomoob.embedded.State;
import com.gomoob.embedded.response.Response;

/**
 * Command used to completely terminate the wrapping server.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class TerminateCommand extends AbstractCommand {

	/**
	 * {@inheritDoc}
	 */
	public IResponse run(IContext context) throws CommandException {

		// If the Mongod executable is available (which means its started) then
		// stop it.
		if (context.getMongoContext().getMongodExecutable() != null) {

			// Sets the STOPPING state
			context.setState(State.STOPPING);
			
			context.getMongoContext().getMongodExecutable().stop();

			// Sets the STOPPED state
			context.setState(State.STOPPED);
			
		}

		// Creates the response
		IResponse response = new Response();
		response.setTerminationRequired(true);

		return response;
	}

}
