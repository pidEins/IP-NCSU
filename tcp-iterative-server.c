/* A simple server in the internet domain using TCP
   The port number is passed as an argument */
#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>

#define SIZE 1000

#define REPLY_WITH_NULL "I got your message\0"

void error(char *msg)
{
    perror(msg);
    exit(1);
}

int main(int argc, char *argv[])
{
     int sockfd, newsockfd, portno, clilen;
     char buffer[1000];
     struct sockaddr_in serv_addr, cli_addr;
     int n;
     if (argc < 2) {
         fprintf(stderr,"usage: %s <port>\n", argv[0]);
         exit(1);
     }
     sockfd = socket(AF_INET, SOCK_STREAM, 0);
     if (sockfd < 0)
        error("ERROR opening socket");
     bzero((char *) &serv_addr, sizeof(serv_addr));
     portno = atoi(argv[1]);
     serv_addr.sin_family = AF_INET;
     serv_addr.sin_addr.s_addr = INADDR_ANY;
     serv_addr.sin_port = htons(portno);
     if (bind(sockfd, (struct sockaddr *) &serv_addr,
              sizeof(serv_addr)) < 0)
              error("ERROR on binding");
     listen(sockfd,5);
     clilen = sizeof(cli_addr);
     for(;;){
        newsockfd = accept(sockfd, (struct sockaddr *) &cli_addr, &clilen);
        if (newsockfd < 0)
             error("ERROR on accept");

	n=0;
        while(1)
	{
		bzero(buffer,1000);
        	n += read(newsockfd,buffer+n,999-n);
		if (n < 0) error("ERROR reading from socket");
       		if ( buffer[n-1] == '\0' )
		{
			break;
		}
       	}
	
	printf("Here is the message: %s\n",buffer);
        n = write(newsockfd,REPLY_WITH_NULL,19);
        if (n < 0) error("ERROR writing to socket");
        close (newsockfd);
     }
     close(sockfd);
     return 0;
}


