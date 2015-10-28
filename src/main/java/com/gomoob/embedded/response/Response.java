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
	 * Indicate if the embedded Mongo DB server should be stopped after the
	 * response is returned.
	 */
	protected boolean stopRequired = false;
	
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
	public boolean isStopRequired() {
		return this.stopRequired;
	}

	/**
	 * {@inheritDoc}
	 */
	public void setStopRequired(boolean stopRequired) {
		this.stopRequired = stopRequired;
	}

	/**
	 * {@inheritDoc}
	 */
	public String toJSON() {
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("stopRequired", this.stopRequired);
		
		return jsonObject.toString();

	}
}
