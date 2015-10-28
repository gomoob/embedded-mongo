/**
 * gomoob/embedded-mongo
 *
 * @copyright Copyright (c) 2015, GOMOOB SARL (http://gomoob.com)
 * @license   http://www.opensource.org/licenses/mit-license.php MIT (see the LICENSE.md file)
 */
package com.gomoob.embedded;

/**
 * Exception common to all commands.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public class CommandException extends Exception {

	/**
	 * A default serial version UID.
	 */
	private static final long serialVersionUID = 1L;

	/**
     * Constructs a new exception with the specified cause and a detail message of <tt>(cause==null ? null : 
     * cause.toString())</tt> (which typically contains the class and detail message of <tt>cause</tt>). This 
     * constructor is useful for exceptions that are little more than wrappers for other throwables (for example, 
     * {@link java.security.PrivilegedActionException}).
     *
     * @param cause the cause (which is saved for later retrieval by the {@link #getCause()} method).  (A 
     *        <tt>null</tt> value is permitted, and indicates that the cause is nonexistent or unknown.)
     */
    public CommandException(Throwable cause) {
        super(cause);
    }
}
