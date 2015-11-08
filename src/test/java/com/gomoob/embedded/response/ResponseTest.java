package com.gomoob.embedded.response;

import static org.junit.Assert.*;

import org.json.JSONObject;
import org.junit.Test;

import com.gomoob.embedded.IResponse;

/**
 * Test case for the {@link Response} class.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class ResponseTest {

	/**
	 * Test method for {@link Response#toJSON()}.
	 */
	@Test
	public void testToJSON() {

		// Test no parameters
		IResponse response = new Response();
		assertEquals("{}", response.toJSON());

		// Test with simple parameters
		response = new Response();
		response.addParameter("string_parameter", "string_value");
		response.addParameter("float_parameter", 0.01);
		response.addParameter("integer_parameter", 10);
		response.addParameter("boolean_parameter", true);

		//@formatter:off
		assertEquals(
		    "{" + 
		        "\"boolean_parameter\":true," + 
		        "\"integer_parameter\":10," + 
		        "\"string_parameter\":\"string_value\"," + 
		        "\"float_parameter\":0.01" + 
		    "}", 
		    response.toJSON()
		);
		//@formatter:on

		// Test with complex parameters
		response = new Response();
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("string_parameter", "string_value");
		jsonObject.put("float_parameter", 0.01);
		jsonObject.put("integer_parameter", 10);
		jsonObject.put("boolean_parameter", true);
		response.addParameter("net", jsonObject);

		//@formatter:off
		assertEquals(
		    "{" + 
		        "\"net\":{" + 
		            "\"boolean_parameter\":true," + 
		            "\"integer_parameter\":10," + 
		            "\"string_parameter\":\"string_value\"," + 
		            "\"float_parameter\":0.01" +
		        "}" + 
		    "}", 
		    response.toJSON()
	    );
		//@formatter:on

	}

}
