/*
/  ASSIGNMENT 3 - ANJOPOWE
/  CREATED: 9/25/2014
/  MODIFIED: 9/26/2014
/  USES SOCKET API TO CREATE "CONNECTIONLESS" CLIENT/SERVER PROTOCOL OVER A TCP SOCKET
/  THIS FILE CREATES THE CLIENT
/  INPUTS: IP ADDRESS (string), PORT (int) 
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>

//the following function "main" creates the client for the 'connection'
int main (int argc, char *argv[]) {

	//char *userInput;
	struct sockaddr_in ServerAdd;

	//printf("Enter one of the following now: 'hello', 'goodbye', or 'exit' \n");
	//scanf("%s", userInput);

	//creates socket
	int sock;
	sock = socket(AF_INET, SOCK_STREAM, 0);
	short port = (short) atoi(argv[2]);

	ServerAdd.sin_family = AF_INET;
	ServerAdd.sin_addr.s_addr = inet_addr(argv[1]);
	ServerAdd.sin_port = htons(port);

	

	//connects to server; dies if fails
	connect(sock, (struct sockaddr *) &ServerAdd, sizeof(ServerAdd));




	while (1) {
		//get user input
		char userInput[256];

		printf("Enter one of the following now: 'hello', 'goodbye', or 'exit' \n");
		scanf("%s", userInput);
		
		//get length of input string for send function
		int inputLength = strlen(userInput);

		//sends message to server
		send(sock, userInput, inputLength, 0);


		//read response from server
		char responseBuff[256];
		int responseLen;
	//	memset(responseBuff, '\0', 256);
		responseLen = recv(sock, responseBuff, 256, 0);

		puts(responseBuff);

		//close socket if one of key phrases
		if (strcmp(responseBuff, "world") == 0) { 
 	       break; 
 	    } 
 	    if (strcmp(responseBuff, "farewell") == 0) { 
 	       break; 
 	    }
 	   if (strcmp(responseBuff, "ok") == 0) { 
 	       break; 
 	   } 
	}
	//close socket
	shutdown(sock, 2);
	//terminate
	return 0;

}