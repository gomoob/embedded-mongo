/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

import java.util.Map;

import de.flapdoodle.embed.mongo.MongodExecutable;

/**
 * Interface which represents a command to be send to the embedded Mongo DB server program socket.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public interface ICommand 
{
	/**
	 * Add a new command parameter.
	 * 
	 * @param name The name of the parameter.
	 * @param value The value of the parameters.
	 */
	public void addParameter(String name, Object value);
	
	/**
	 * Gets the name of the command.
	 * 
	 * @return The name of the command.
	 */
    public String getName();

    /**
     * Gets the command parameters.
     * 
     * @return The command parameters.
     */
    public Map<String, Object> getParameters();

    /**
     * Sets the Mongo DB executable currently in execution. 
     * 
     * @param mongodExecutable the Mongo DB executable currently in execution.
     */
    public void setMongodExecutable(MongodExecutable mongodExecutable);
    
    /**
     * Sets the name of the command.
     * 
     * @param name The name of the command.
     */
    public void setName(String name);

    /**
     * Run the command.
     * 
     * @param commandContext The execution context.
     * 
     * @return The resulting response.
     * 
     * @throws CommandException An exception thrown if an error occured while executing the command.
     */
    public IResponse run(IContext commandContext) throws CommandException;
}
