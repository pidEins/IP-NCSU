import java.io.*; 
import java.net.*; 

class TCPClient { 

    public static void main(String argv[]) throws Exception 
    {
	char bytes; 
        String sentence = ""; 
        String modifiedSentence = ""; 

        BufferedReader inFromUser = 
          new BufferedReader(new InputStreamReader(System.in)); 

        Socket clientSocket = new Socket("127.0.0.1", 9678); 

        DataOutputStream outToServer = 
          new DataOutputStream(clientSocket.getOutputStream()); 
        
        BufferedReader inFromServer = 
          new BufferedReader(new
          InputStreamReader(clientSocket.getInputStream())); 

        System.out.println("Enter string to send:");
        sentence = inFromUser.readLine(); 

        outToServer.writeBytes(sentence + '\n' + '\0'); 

	/* Read char by char till we will not receive \0 */
	bytes = (char)inFromServer.read();
        while ( bytes != '\0')
	{
		/* append the char to the main string */
		modifiedSentence += bytes;
        	bytes = (char)inFromServer.read();
	} 

        System.out.println("FROM SERVER: " + modifiedSentence); 

        clientSocket.close(); 
                   
    } 
} 


