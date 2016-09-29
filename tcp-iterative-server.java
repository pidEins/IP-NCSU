import java.io.*; 
import java.net.*; 

class TCPServer { 

  public static void main(String argv[]) throws Exception 
    { 
      char bytes;
      String clientSentence = ""; 
      String capitalizedSentence; 

      ServerSocket welcomeSocket = new ServerSocket(9678); 
  
      while(true) { 
  
           Socket connectionSocket = welcomeSocket.accept(); 

           BufferedReader inFromClient = 
              new BufferedReader(new
              InputStreamReader(connectionSocket.getInputStream())); 

           DataOutputStream  outToClient = 
             new DataOutputStream(connectionSocket.getOutputStream()); 
	
	  /* read char by char till we will not get \0 */
	  bytes = (char)inFromClient.read();
          while( bytes != '\0' )
	  {	
		/* append the char to the main string */
		clientSentence += bytes; 
	  	bytes = (char)inFromClient.read(); 
	  }

           capitalizedSentence = clientSentence.toUpperCase() + '\n'; 
           System.out.println (capitalizedSentence);

	   /* Append \0 at the end of the string before writing*/
           outToClient.writeBytes(capitalizedSentence + '\0'); 
        
	  clientSentence="";
	} 
    } 
} 
