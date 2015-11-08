/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.Writer;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.json.JSONObject;

import com.gomoob.embedded.command.GetConfigurationCommand;
import com.gomoob.embedded.command.GetStateCommand;
import com.gomoob.embedded.command.StartCommand;
import com.gomoob.embedded.command.StopCommand;
import com.gomoob.embedded.command.TerminateCommand;
import com.gomoob.embedded.context.Context;

/**
 * Main class of the embedded Mongo DB server.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class EmbeddedMongo {

	/**
	 * A map of the commands supported by the embedded Mongo DB server.
	 */
	private static Map<String, ICommand> commands = new HashMap<String, ICommand>();

	/**
	 * An object which describes the while execution context and configuration.
	 */
	private static IContext context;

	static {

		commands.put("get-configuration", new GetConfigurationCommand());
		commands.put("get-state", new GetStateCommand());
		commands.put("start", new StartCommand());
		commands.put("stop", new StopCommand());
		commands.put("terminate", new TerminateCommand());

	}

	/**
	 * Function used to parse the command line.
	 * 
	 * @param args Arguments passed at command line.
	 * 
	 * @throws Exception
	 */
	private static void parseCommandLine(String[] args) throws Exception {
		
		CommandLineParser parser = new DefaultParser();

		Options options = new Options();
		options.addOption("mp", "mongo-port", true, "The port used by Mongo DB.");
		options.addOption("mv", "mongo-version", true, "The version of Mongo DB to use.");
		options.addOption("sp", "socket-port", true, "The port number used by the server socket.");

		CommandLine commandLine = parser.parse(options, args);

		// Read the mongo port
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

		boolean terminated = false;

		System.out.println("MONGOD_HOST=" + context.getMongoContext().getNet().getServerAddress().getHostName());
		System.out.println("MONGOD_PORT=" + context.getMongoContext().getNet().getPort());
		System.out.println("SERVER_SOCKET_PORT=" + context.getSocketContext().getServerSocket().getLocalPort());

		Socket socket = null;
		BufferedReader reader = null;
        Writer writer = null;
		
		while (!terminated) {

			socket = context.getSocketContext().getServerSocket().accept();

			reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			writer = new OutputStreamWriter(new DataOutputStream(socket.getOutputStream()));

			System.out.println("Waiting for command...");
			String commandString = reader.readLine();
			System.out.println(commandString);

			ICommand command = parseCommandString(commandString);

			IResponse response = command.run(context);

			writer.write(response.toJSON());
			
			writer.close();

			// If the current socket is opened close it
			if (!socket.isClosed()) {
				socket.close();
			}

			// If stop is required
			terminated = response.isTerminationRequired();

		}

		context.getSocketContext().getServerSocket().close();
	}

	/**
	 * Utility function used to get a command from a command string (i.e a JSON
	 * string which completely describe a command).
	 * 
	 * @param commandString A json string which completely describe a command to be executed.
	 * 
	 * @return The command to execute.
	 */
	private static ICommand parseCommandString(String commandString) {
		ICommand command = null;

		JSONObject commandJsonObject = new JSONObject(commandString);
		String commandName = commandJsonObject.getString("command");

		System.out.println("Command : " + commandName);

		command = commands.get(commandName);

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
