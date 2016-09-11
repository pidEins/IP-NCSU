import java.io.*; 
import java.net.*; 
import java.util.Arrays;
  
class UDPClient { 
    public static void main(String args[]) throws Exception 
    { 
     
      String IP;  
      if (args.length > 0) {
    	try {
           IP = args[0];
    	} catch (NumberFormatException e) {
           System.err.println("usage: java UDPClient <IP address>");
           System.exit(1);
    	}
      }
      else
      {
           System.err.println("usage: java UDPClient <IP address>");
           System.exit(1);
      }
 
      BufferedReader inFromUser = 
        new BufferedReader(new InputStreamReader(System.in)); 
  
      DatagramSocket clientSocket = new DatagramSocket(); 
  
      InetAddress IPAddress = InetAddress.getByName(args[0]); 
  
      byte[] sendData = new byte[1000]; 
      byte[] receiveData = new byte[1000]; 
  
      System.out.println("Enter string to send:");
   
      String sentence = inFromUser.readLine();
      while( sentence != null )
      { 
      	try {
      	  sentence = sentence + "\n";
	  sendData = sentence.getBytes();         
      	  DatagramPacket sendPacket = 
        	 new DatagramPacket(sendData, sendData.length, IPAddress, 8222); 
  
      	  clientSocket.send(sendPacket); 
  
      	  DatagramPacket receivePacket = 
         	new DatagramPacket(receiveData, receiveData.length); 
  
      	  clientSocket.receive(receivePacket); 
  
      	  String modifiedSentence = 
          	new String(receivePacket.getData()); 
  
      	  System.out.print("Received from server: " + modifiedSentence); 
      	  System.out.println("Enter string to send:");
      	  sentence = inFromUser.readLine();
	} catch (IOException e){
	  System.exit(1);
	}

	Arrays.fill( sendData, (byte) 0 );
	Arrays.fill( receiveData, (byte) 0 );
      } 
//      clientSocket.close(); 
   }
} 
