/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.command;

import java.util.HashMap;
import java.util.Map;

import com.gomoob.embedded.ICommand;

import de.flapdoodle.embed.mongo.MongodExecutable;

/**
 * Abstract class common to all socket commands.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public abstract class AbstractCommand implements ICommand {

	/**
	 * A reference to the Mongo DB executable currently in execution. 
	 */
	protected MongodExecutable mongodExecutable = null;
	
	/**
	 * The name of the command.
	 */
	protected String name = null;
	
	/**
	 * The parameters of the command.
	 */
	protected Map<String, Object> parameters = new HashMap<String, Object>();

	/**
	 * {@inheritDoc}
	 */
	public void addParameter(String name, Object value) 
	{
       this.parameters.put(name, value);
	}
	
	/**
	 * {@inheritDoc}
	 */
	public String getName() 
	{
		return this.name;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> getParameters()
	{
		return this.parameters;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setMongodExecutable(MongodExecutable mongodExecutable)
	{
		this.mongodExecutable = mongodExecutable;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public void setName(String name)
	{
		this.name = name;
	}
}
