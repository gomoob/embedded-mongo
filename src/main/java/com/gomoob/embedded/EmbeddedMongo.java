/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.json.JSONObject;

import com.gomoob.embedded.command.GetConfigurationCommand;
import com.gomoob.embedded.command.StopCommand;
import com.gomoob.embedded.context.Context;

import de.flapdoodle.embed.mongo.MongodExecutable;
import de.flapdoodle.embed.mongo.MongodProcess;
import de.flapdoodle.embed.mongo.MongodStarter;

/**
 * Main class of the embedded Mongo DB server.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class EmbeddedMongo {

	private static Map<String, ICommand> commands = new HashMap<String, ICommand>();

	private static MongodExecutable mongodExecutable = null;

	/**
	 * An object which describes the while execution context and configuration.
	 */
	private static IContext context;

	static {

		commands.put("get-configuration", new GetConfigurationCommand());
		commands.put("stop", new StopCommand());

	}

	private static void startMongo() throws Exception {

		MongodStarter starter = MongodStarter.getInstance(context.getMongoContext().getRuntimeConfig());

		mongodExecutable = starter.prepare(context.getMongoContext().getMongodConfig());
		MongodProcess mongod = mongodExecutable.start();

	}
	
	private static void parseCommandLine(String[] args) throws Exception {
		
		CommandLineParser parser = new DefaultParser();

		Options options = new Options();
		options.addOption("mp", "mongo-port", true, "The port used by Mongo DB.");
		options.addOption("mv", "mongo-version", true, "The version of Mongo DB to use.");
		options.addOption("sp", "socket-port", true, "The port number used by the server socket.");

		CommandLine commandLine = parser.parse(options, args);

		// Read he mongo port
		if (commandLine.hasOption("mongo-port")) {
			
			context.getMongoContext().setPort(Integer.parseInt(commandLine.getOptionValue("mongo-port")));

		}
		
		// Reads the socket port
		if (commandLine.hasOption("socket-port")) {

			context.getSocketContext().setPort(Integer.parseInt(commandLine.getOptionValue("socket-port")));

		}
		
	}

	/**
	 * Main entry of the program.
	 * 
	 * @param args arguments used to customize starting.
	 * 
	 * @throws Exception If an error occured while executing the server.
	 */
	public static void main(String[] args) throws Exception {

		// Creates a default execution context, then the configuration of this context can be changed depending on the 
		// command line parameters received.
		context = new Context();

		// Parse the command line
		parseCommandLine(args);

		boolean stop = false;

		System.out.println("MONGOD_HOST=" + context.getMongoContext().getNet().getServerAddress().getHostName());
		System.out.println("MONGOD_PORT=" + context.getMongoContext().getNet().getPort());
		System.out.println("SERVER_SOCKET_PORT=" + context.getSocketContext().getServerSocket().getLocalPort());

		// Starts the Mongo server
		startMongo();
		
		while (!stop) {

			Socket socket = context.getSocketContext().getServerSocket().accept();

			InputStreamReader inputStream = new InputStreamReader(socket.getInputStream());
			DataOutputStream outputStream = new DataOutputStream(socket.getOutputStream());
			BufferedReader inputReader = new BufferedReader(inputStream);

			System.out.println("Waiting for command...");
			String commandString = inputReader.readLine();
			System.out.println(commandString);

			ICommand command = parseCommandString(commandString);

			IResponse response = command.run(new Context());

			// Sends a representation of the response
			outputStream.writeBytes(response.toJSON());
			outputStream.flush();

			// If the current socket is opened close it
			if (!socket.isClosed()) {
				socket.close();
			}

			// If stop is required
			stop = response.isStopRequired();

		}

		context.getSocketContext().getServerSocket().close();
	}

	/**
	 * Utility function used to get a command from a command string (i.e a JSON
	 * string which completely describe a command).
	 * 
	 * @param commandString
	 *            A json string which completely describe a command to be
	 *            executed.
	 * 
	 * @return The command to execute.
	 */
	private static ICommand parseCommandString(String commandString) {
		ICommand command = null;

		JSONObject commandJsonObject = new JSONObject(commandString);
		String commandName = commandJsonObject.getString("command");

		System.out.println("Command : " + commandName);

		command = commands.get(commandName);

		// Sets several context objects which could be used by the command
		// during run
		command.setMongodExecutable(mongodExecutable);

		// Sets the command parameters
		if (commandJsonObject.has("parameters")) {

			JSONObject parameters = commandJsonObject.getJSONObject("parameters");

			for (String key : parameters.keySet()) {
				command.addParameter(key, parameters.get(key));
			}
		}

		return command;
	}
}
