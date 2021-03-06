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
import com.gomoob.embedded.response.Response;

/**
 * Command used to stop the server.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class StopCommand extends AbstractCommand {

	/**
	 * {@inheritDoc}
	 */
	public IResponse run(IContext context) throws CommandException
	{
		// If the Mongod executable is available (which means its started) then stop it.
		if(context.getMongoContext().getMongodExecutable() != null) {
		
			context.getMongoContext().getMongodExecutable().stop();

		}

		// Creates the response
		IResponse response = new Response();

		return response;
	}
}
