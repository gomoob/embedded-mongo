package com.gomoob.embedded;

/**
 * Enumeration which list the possible states of the server.
 * 
 * @author Baptiste GAILLARD (baptiste.gaillard@gomoob.com)
 */
public enum State {
	
	/**
	 * State defined before the first embedded server start.
	 */
	NEVER_STARTED,
	
	/**
	 * State defined when the embedded server has been started and then stopped.
	 */
    STOPPED,

    /**
     * State defined while the embedded server is stopping.
     */
    STOPPING,

    /**
     * State defined when the embedded server is started.
     */
    STARTED,

    /**
     * State defined when the embedded server is currently starting.
     */
    STARTING,

    /**
     * State defined when the wrapping server is currently terminating.
     */
    TERMINATING,
    
    /**
     * State defined when the server is under an unknown state, in most cases this is an error.
     */
    UNKNOWN
}
