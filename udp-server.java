import java.io.*; 
import java.net.*; 
import java.util.Arrays;
  
class UDPServer { 
  public static void main(String args[]) throws Exception 
    { 
  
      DatagramSocket serverSocket = new DatagramSocket(1153); 
  
      byte[] receiveData = new byte[1001]; 
//      byte[] sendData = new byte[1001]; 
  
      while(true) 
        { 
 
          DatagramPacket receivePacket = 
             new DatagramPacket(receiveData, receiveData.length); 
           serverSocket.receive(receivePacket); 

          String sentence = new String(receivePacket.getData()); 
  
          InetAddress IPAddress = receivePacket.getAddress(); 
  
          String capitalizedSentence = sentence.toUpperCase(); 

          int port = receivePacket.getPort(); 
 
          System.out.println ("------------------------------------------------");
          System.out.println ("Received the following:");
          System.out.println (sentence);
          System.out.println ("------------------------------------------------");


	  receiveData = sentence.getBytes(); 
  
          DatagramPacket sendPacket = 
             new DatagramPacket(receiveData, receiveData.length, IPAddress, 
                               port); 
  
          serverSocket.send(sendPacket);
	  Arrays.fill( receiveData, (byte) 0 );
        } 
    } 
}  


