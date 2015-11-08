# embedded-mongo

> Start an embedded Mongo DB server and drive it using server sockets.

## Introduction

The Java ecosystem offers several solutions to start an embedded Mongo DB server and easily write unit and integration 
tests. Sadly those embedded Mongo DB servers are not available in other languages like PHP, Javascript, etc.

This project try to provide a solution to write Mongo DB unit and integration tests in other languages by driving a 
Mongo DB server with server sockets.

Using this embedded Mongo DB server should then be as easy as : 

 * Starts the embedded Mongo DB server ; 
 * Wait for the embedded Mongo DB server to be started ; 
 * (Optional) Import some testing data ; 
 * Run unit test ; 
 * Stops the embedded Mongo DB server.
 
## Architecture

## Commands 

### `get-configuration`

The `get-configuration` command allows to get the current server configuration. 

The payload of to this command is the following. 
```json
{
    "command" : "get-configuration"
}
```

### `get-status`

The `get-status` command allows to get the current server status.

The payload of this command is the following.
```json
{
    "command" : "get-status",
    "status" : "STOPPED"
}
```

The status provided by the server can be equal to he following values : 

 * `STARTING`
 * `STARTED`
 * `STOPPING`
 * `STOPPED`
 * `UNKNOWN`

### `start`

The `start` command is used to start the server.

The payload of this command is the following.

```json
{
    "command" : "start"
}
```

### `stop`

The `stop` command is used to stop the server.

The payload of this command is the following.

```json
{
    "command" : "stop"
}
```

## Build commands

## Release history

### 1.0.0 (2015-11-08)
 * First release.
