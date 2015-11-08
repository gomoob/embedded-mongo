/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded.command;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.json.JSONObject;

import com.gomoob.embedded.CommandException;
import com.gomoob.embedded.IContext;
import com.gomoob.embedded.IMongoContext;
import com.gomoob.embedded.IResponse;
import com.gomoob.embedded.response.Response;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.process.extract.IExtractedFileSet;

/**
 * Command used to gets the server configuration.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class GetConfigurationCommand extends AbstractCommand {

	/**
	 * {@inheritDoc}
	 */
	public IResponse run(IContext context) throws CommandException
	{
		// Creates the response
		IResponse response = new Response();
		
		response.addParameter("mongo", this.createMongo(context.getMongoContext()));
		
		return response;
	}
	
	private JSONObject createExecutable(MongodExecutable mongodExecutable) {
		
		IExtractedFileSet file = mongodExecutable.getFile();
		
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("baseDirIsGenerated", file.baseDirIsGenerated());
		jsonObject.put("baseDir", file.baseDir().getAbsolutePath());
		jsonObject.put("executable", file.executable().getAbsolutePath());
		
		return jsonObject;
		
	}
	
	private JSONObject createMongo(IMongoContext mongoContext) throws CommandException {
		
		JSONObject jsonObject = new JSONObject();
		
		jsonObject.put("net", this.createNet(mongoContext));		
		jsonObject.put("pidFile", mongoContext.getMongodConfig().pidFile());
		jsonObject.put("processId", mongoContext.getMongodProcess().getProcessId());
		jsonObject.put("executable", this.createExecutable(mongoContext.getMongodExecutable()));
		
		return jsonObject;
		
	}
	
	private JSONObject createNet(IMongoContext mongoContext) throws CommandException {
		
		String bindIp = mongoContext.getMongodConfig().net().getBindIp();
        int port = mongoContext.getMongodConfig().net().getPort();
		boolean ipv6 = mongoContext.getMongodConfig().net().isIpv6();
        
		JSONObject serverAddress = new JSONObject();
		
		try {
			InetAddress inetAddress = mongoContext.getMongodConfig().net().getServerAddress();
			serverAddress.put("canonicalHostName", inetAddress.getCanonicalHostName());
			serverAddress.put("hostAddress", inetAddress.getHostAddress());
			serverAddress.put("hostName", inetAddress.getHostName());
		} catch(UnknownHostException uhex) {
			throw new CommandException(uhex);
		}

		JSONObject net = new JSONObject();
		net.put("bindIp", bindIp);
		net.put("port", port);
		net.put("ipv6", ipv6);
		net.put("serverAddress", serverAddress);
		
		return net;
		
	}
}
