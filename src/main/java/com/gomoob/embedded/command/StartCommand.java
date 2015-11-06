/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.command;

import java.io.IOException;

import com.gomoob.embedded.CommandException;
import com.gomoob.embedded.IContext;
import com.gomoob.embedded.IResponse;
import com.gomoob.embedded.State;
import com.gomoob.embedded.response.Response;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;

/**
 * Command used to start the server.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class StartCommand extends AbstractCommand {

	/**
	 * {@inheritDoc}
	 */
	public IResponse run(IContext context) throws CommandException
	{
		// Creates the response
		IResponse response = new Response();
		
		// If the embedded server is not already stated or starting
		if(context.getState() != State.STARTING && context.getState() != State.STARTED) {
		
			// Sets the starting state
			context.setState(State.STARTING);
	
			// Creates and starts a MongodExecutable
			MongodStarter starter = MongodStarter.getInstance(context.getMongoContext().getRuntimeConfig());
			MongodExecutable mongodExecutable = starter.prepare(context.getMongoContext().getMongodConfig());
			
			try {
				MongodProcess mongodProcess = mongodExecutable.start();
				context.getMongoContext().setMongodProcess(mongodProcess);
			} catch (IOException ioException) {
				throw new CommandException(ioException);
			}
	
			context.getMongoContext().setMongodExecutable(mongodExecutable);

			// Sets the started state
			context.setState(State.STARTED);

		}

		// Otherwise this is an error because the server is already started
		else {
			
			// TODO: Trouver un moyen de modéliser une erreur générique
			
		}

		return response;
	}
}
