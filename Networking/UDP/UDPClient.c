/*
/  ASSIGNMENT 4 - ANTHONY POWELL - ANJOPOWE
/  CREATED: 10/8/2014
/  MODIFIED: 10/9/2014
/  CREATES 'CONNECTION' FOR UDP CLIENT/SERVER PROTOCOL
/  THIS FILE CREATES THE CLIENT
/  INPUTS: IP ADDRESS (string), PORT (int) 
*/

#include <stdio.h>
#include <stdlib.h>
#include <sys/socket.h>
#include <arpa/inet.h>
#include <string.h>



int main(int argc, char *argv[]) {

	int sock;
	int buffer = 512;
	char b[buffer];
	struct sockaddr_in ServerAdd;

	char *packetsRec[1024]; //controls and places packets in order; regardless of if received in incorrect order; holds up to 1024 strings (also max client sent number) **temp
	unsigned int len = sizeof(ServerAdd);

	short port = (short) atoi(argv[2]);

	if (argc < 3) {
		printf("Incorrect arguments.");
		return 1; //failed; incorrect args
	}

	if ((sock = socket(AF_INET, SOCK_DGRAM, IPPROTO_UDP)) == -1) {
		printf("Socket creation failed.");
		return 1; //failed to create socket
	}

	//socket created

	memset((char *) &ServerAdd, 0, sizeof(ServerAdd));

	//setup sockadd_in struct ServerAdd (Server Address)
    ServerAdd.sin_family = AF_INET;
    //ServerAdd.sin_addr.s_addr = inet_addr(argv[1]); 
    ServerAdd.sin_port= htons(port);
   // ServerAdd.sin_port = htons(32000);

    if (inet_aton(argv[1], &ServerAdd.sin_addr) == 0) {
    	printf("Inet_aton failed.");
    	return 1; //failed: Inet_aton 
    }

	char messageSend[256];
	//int sendTries = 0;


	int i; //counter: for loop

	while (1) {
		//takes user input/message
		printf("Enter one of the following now: 'hello', 'goodbye', or 'exit' \n");
		scanf("%s", messageSend);
		int sendLen = strlen(messageSend);
		
		//tries to send up to 5 times, fails if not after 5 tries
		for (i = 1; i <= 5; i++) {
			if (sendto(sock, messageSend, sendLen, 0, (struct sockaddr *) &ServerAdd, len) == -1) {
				printf("Failed to send.");
				return 1; //send failed
			}
			break; //breaks b/c if it makes it here, it sent succesfully
		}

		memset(b, '\0', buffer);

		if (recvfrom(sock, b, buffer, 0, (struct sockaddr *) &ServerAdd, &len) == -1) {
			printf("Failed to receive message.");
			exit(1); //failed to receive data

		}

		puts(b); //output message from server

		if (strcmp(b, "world") == 0) {
            break; 
        }
        if (strcmp(b, "farewell") == 0) {
            break; 
        }
        if (strcmp(b, "ok") == 0) {
            break; 
        }

	}
		
	shutdown(sock, 1); //close socket
	return 0;
}