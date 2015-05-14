/*
/  ASSIGNMENT 4 - ANTHONY POWELL - ANJOPOWE
/  CREATED: 10/8/2014
/  MODIFIED: 10/9/2014
/  CREATES 'CONNCETION' FOR UDP CLIENT/SERVER PROTOCOL
/  THIS FILE CREATES THE SERVER
/  INPUTS: PORT (int) 
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>

int main(int argc, char *argv[]) {


	int buffer = 512;
	int sock;
	struct sockaddr_in ServerAdd, ClientAdd;
	unsigned int len = sizeof(ClientAdd);
	
	if (argc < 2) {
		printf("Incorrect args.\n");
		exit(1); //incorrect args; failed
	}

	//create socket
	if ((sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) {
		printf("Failed to create socket.\n");
		exit(1); //failed to create socket
	}

	//setup ServerAdd struct
	short port = (short) atoi(argv[1]);
	ServerAdd.sin_family = AF_INET;
	ServerAdd.sin_addr.s_addr = htonl(INADDR_ANY);
	ServerAdd.sin_port = htons(port);

	//bind socket to port
	if (bind(sock, (struct sockaddr *) &ServerAdd, sizeof(ServerAdd)) == -1) {
		printf("failed to bind.\n");
		exit(1); //failed to bind
	}

	socklen_t addLen;

	//int cSocket;

	int i;
	char b[buffer];
	int recLen;
	//loop - wait for connections
	while (1) {


		fflush(stdout);
		//receive message

		//try to get message up to 5 times, fails after 5 tries
		for (i = 0; i <= 5; i++) {
			if ((recLen = recvfrom(sock, b, buffer, 0, (struct sockaddr *) &ClientAdd, &addLen)) == -1) {
				printf("failed to receive message.\n");
				exit(1); //failed to receive message
			}
			break;
		}

		//decide which response is appropriate for the client
		if (strcmp(b, "exit") == 0) {
			strcpy(b, "ok");
		} else if (strcmp(b, "hello")) {
			strcpy(b, "world");
		} else if (strcmp(b, "goodbye")) {
			strcpy(b, "farewell");
		} else { 
			strcpy(b, "unknown");
		}
		int sendLen = strlen(b);

		//send response back to client
		if (sendto(sock, b, sendLen, 0, (struct sockaddr*) &ClientAdd, len) == -1) {
			printf("failed to send");
			exit(1); //send failed
		}

	}

	//close socket
	shutdown(sock, 2);
	return 0;

}