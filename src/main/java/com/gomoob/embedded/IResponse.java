/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

import java.util.Map;

/**
 * Interface which represents a response received from the program socket of the embedded Mongo DB server program.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public interface IResponse {

	/**
	 * Add a new response parameter.
	 * 
	 * @param name The name of the parameter.
	 * @param value The value of the parameters.
	 */
	public void addParameter(String name, Object value);
	
	/**
     * Gets the response parameters.
     * 
     * @return The response parameters.
     */
    public Map<String, Object> getParameters();
	
	/**
	 * Function used to indicate if the embedded Mongo DB server should be stopped after the response is returned.
	 * 
	 * @return <code>true</code> if the embedded Mongo DB server should be stopped after the response is returned, 
	 *         <code>false</code> otherwise.
	 */
	public boolean isStopRequired();
	
	/**
	 * Sets if the embedded Mongo DB server should be stopped after the response is returned.
	 * 
	 * @param stopRequired <code>true</code> to require stop, <code>false</code> otherwise.
	 */
	public void setStopRequired(boolean stopRequired);
	
	/**
	 * Returns a JSON representation of the response.
	 * 
	 * @return A JSON representation of the response.
	 */
	public String toJSON();
}
