import java.net.*; 
import java.io.*; 

public class ChatClientThread extends Thread {     
    private ChatClient client = null;     
    private Socket socket = null;     
    private DataInputStream in = null;     

    public ChatClientThread(ChatClient cli, Socket sock) { 
        this.client = cli; 
        this.socket = sock; 

        try { 
            in = new DataInputStream(socket.getInputStream()); 
        } catch (IOException e) { 
            System.out.println("Error getting input stream: " + e.getMessage()); 
            client.stop(); 
        }         
        start(); 
    }     

    public void close() {         
        try {             
            if (in != null) 
                in.close(); 
        } catch (IOException e) { 
            System.out.println("Error closing input stream: " + e.getMessage()); 
        } 
    }     

    public void run() {         
        while (true) { 
            try { 
                client.handle(in.readLine()); 
            } catch (IOException e) { 
                System.out.println("Listening error: " + e.getMessage());                 
                client.stop(); 
            } 
        } 
    } 
}
