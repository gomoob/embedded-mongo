<?php 

$address = 'localhost';
$port = 4309;

$socket = socket_create(AF_INET, SOCK_STREAM, getprotobyname('tcp'));
socket_connect($socket, $address, $port);

$message = '{"command" : "' . $argv[1] . '"}' . PHP_EOL;
$len = strlen($message);

$status = socket_sendto($socket, $message, $len, 0, $address, $port);

if($status !== FALSE)
{
	$message = '';
	$next = '';

	while ($next = socket_read($socket, 4096))
	{
		$message .= $next;
	}
	
	$json = json_decode($message, true);
    echo json_encode($json, JSON_PRETTY_PRINT);
    echo "\n";
}
else
{
	echo "Failed";
}

socket_close($socket);
