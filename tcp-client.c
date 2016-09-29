#include <stdio.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <netdb.h> 

#define MAX_SIZE 10000

void error(char *msg)
{
    perror(msg);
    exit(0);
}

int main(int argc, char *argv[])
{
    int sockfd, portno, n;

    struct sockaddr_in serv_addr;
    struct hostent *server;

    char buffer[MAX_SIZE];
    if (argc < 3) {
       fprintf(stderr,"usage: %s <hostname> <port>\n", argv[0]);
       exit(0);
    }
    portno = atoi(argv[2]);
    sockfd = socket(AF_INET, SOCK_STREAM, 0);
    if (sockfd < 0) 
        error("ERROR opening socket");
    server = gethostbyname(argv[1]);
    if (server == NULL) {
        fprintf(stderr,"ERROR, no such host\n");
        exit(0);
    }
    bzero((char *) &serv_addr, sizeof(serv_addr));
    serv_addr.sin_family = AF_INET;
    bcopy((char *)server->h_addr, 
         (char *)&serv_addr.sin_addr.s_addr,
         server->h_length);
    serv_addr.sin_port = htons(portno);
    if (connect(sockfd,(struct sockaddr *)&serv_addr,sizeof(serv_addr)) < 0) 
        error("ERROR connecting");
    printf("Please enter the message: ");
    
    /* assing 0 to each bits */
    bzero(buffer,MAX_SIZE);
    
    /* assign the buffer till MAX_SIZE - 1 because 
    we want to send the \0 as terminating character */
    fgets(buffer,MAX_SIZE-1,stdin);
    n = write(sockfd,buffer,strlen(buffer)+1);
	
    if (n < 0) 
         error("ERROR writing to socket");

    bzero(buffer,MAX_SIZE);
    n = read(sockfd,buffer,MAX_SIZE-1);
    if (n < 0) 
         error("ERROR reading from socket");
    printf("%s\n",buffer);
    close (sockfd);
    return 0;
}

