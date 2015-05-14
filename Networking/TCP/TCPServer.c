/*
/  ASSIGNMENT 3 - ANJOPOWE
/  CREATED: 9/25/2014
/  MODIFIED: 9/26/2014
/  USES SOCKET API TO CREATE "CONNECTIONLESS" CLIENT/SERVER PROTOCOL OVER A TCP SOCKET
/  THIS FILE CREATES THE SERVER
/  INPUTS: PORT (int) 
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>
#include <unistd.h>

//the following function "main" creates the server for the 'connection'
int main (int argc, char *argv[]) {

	//creates server socket; dies if fails
	int sock;
	struct sockaddr_in ServerAdd;

	sock = socket(AF_INET, SOCK_STREAM, 0);
	short port = (short) atoi(argv[1]);
	//setup sockaddr_in struct ServerAdd (Server Address)
	ServerAdd.sin_family = AF_INET;
	ServerAdd.sin_addr.s_addr = INADDR_ANY;
	ServerAdd.sin_port = htons(port); 

	//bind socket to port
	bind(sock, (struct sockaddr *) &ServerAdd, sizeof(ServerAdd));

	//listen for connections
	listen(sock, 10);

	//accept connection from client
	int cSocket;
	struct sockaddr_in ClientAdd;

	int addLen = sizeof(struct sockaddr_in);
	cSocket = accept(sock, (struct sockaddr *) &ClientAdd, (socklen_t*) &addLen);	

	//receive message from client
	char messageBuffer[256];
	int sendLen;
	int messageReceivedLength;
	while ((messageReceivedLength = recv(cSocket, messageBuffer, 256, 0)) > 0) {
//		messageBuffer[messageReceivedLength] = '\0';

		//decide which response is appropriate for the client
		if (strcmp(messageBuffer, "exit") == 0) {
			strcpy(messageBuffer, "ok");
		} else if (strcmp(messageBuffer, "hello") == 0) {
			strcpy(messageBuffer, "world");
		} else if (strcmp(messageBuffer, "goodbye") == 0) {
			strcpy(messageBuffer, "farewell");
		} else { 
			strcpy(messageBuffer, "unknown");
		}
		//send response
		sendLen = strlen(messageBuffer);
		write(cSocket, messageBuffer, sendLen);

	}

	return 1;
}