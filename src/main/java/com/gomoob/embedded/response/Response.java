/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.response;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.gomoob.embedded.IResponse;

/**
 * Class which represents a command response.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class Response implements IResponse {

	/**
	 * The response parameters.
	 */
	protected Map<String, Object> parameters = new HashMap<String, Object>();
	
	/**
	 * Indicate if the wrapping server should be terminated after the response is returned.
	 */
	protected boolean terminationRequired = false;
	
	/**
	 * {@inheritDoc}
	 */
	public void addParameter(String name, Object value) {
		this.parameters.put(name, value);
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object> getParameters() {
		return this.parameters;
	}
	
	/**
	 * {@inheritDoc}
	 */
	public boolean isTerminationRequired() {
		return this.terminationRequired;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setTerminationRequired(boolean stopRequired) {
		this.terminationRequired = stopRequired;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toJSON() {
		
		JSONObject jsonObject = new JSONObject();

		for(String parameter : this.parameters.keySet()) {
		
			jsonObject.put(parameter, this.parameters.get(parameter));
		
		}
		return jsonObject.toString();

	}
}
