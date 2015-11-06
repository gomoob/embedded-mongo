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
 * Command used to get the server state.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class GetStateCommand extends AbstractCommand {

	/**
	 * {@inheritDoc}
	 */
	public IResponse run(IContext context) throws CommandException {

		// Creates the response
		IResponse response = new Response();
		response.setTerminationRequired(false);

		response.addParameter("state", context.getState());

		return response;

	}

}
