#include <stdio.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>
#include <string.h>

char *strupr(char *str)
{
  unsigned char *p = (unsigned char *)str;
  
  while (*p) {
     *p = toupper(*p);
      p++;
  }

  return str;
}

int main(int argc, char**argv)
{
   int sockfd,n;
   struct sockaddr_in servaddr,cliaddr;
   socklen_t len;
   char mesg[1024];

   sockfd=socket(AF_INET,SOCK_DGRAM,0);

   bzero(&servaddr,sizeof(servaddr));
   servaddr.sin_family = AF_INET;
   servaddr.sin_addr.s_addr=htonl(INADDR_ANY);
   servaddr.sin_port=htons(9876);
   bind(sockfd,(struct sockaddr *)&servaddr,sizeof(servaddr));

   for (;;)
   {
      len = sizeof(cliaddr);
      bzero(mesg, 1024);
      n = recvfrom(sockfd,mesg,1024,0,(struct sockaddr *)&cliaddr,&len);
      sendto(sockfd,strupr(mesg),1024,0,(struct sockaddr *)&cliaddr,sizeof(cliaddr));
      mesg[n] = '\0';
      printf("%s\n", strupr(mesg));
   }
}

