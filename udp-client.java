import java.io.*; 
import java.net.*; 
import java.util.Arrays;
  
class UDPClient { 
    public static void main(String args[]) throws Exception 
    { 
  
      BufferedReader inFromUser = 
        new BufferedReader(new InputStreamReader(System.in)); 
  
      DatagramSocket clientSocket = new DatagramSocket(); 
  
      InetAddress IPAddress = InetAddress.getByName("127.0.0.1"); 
  
      byte[] sendData = new byte[1500]; 
      byte[] receiveData = new byte[1500]; 
      
      System.out.println("Enter string to send:");
      while(1==1)
      {
 
      	String sentence = inFromUser.readLine(); 
      	sendData = sentence.getBytes();         
      	DatagramPacket sendPacket = 
         	new DatagramPacket(sendData, sendData.length, IPAddress, 1153); 
  
      	clientSocket.send(sendPacket); 
  
      	DatagramPacket receivePacket = 
        	new DatagramPacket(receiveData, receiveData.length); 
  
      	clientSocket.receive(receivePacket); 
  
      	String modifiedSentence = 
          	new String(receivePacket.getData()); 
  
      	System.out.println("Received from server: "); 
      	System.out.println(modifiedSentence); 
      	System.out.println("Enter string to send:");
        
	Arrays.fill( receiveData, (byte) 0 );
	} 
      
	//clientSocket.close(); 
    	//return;
    } 
}
