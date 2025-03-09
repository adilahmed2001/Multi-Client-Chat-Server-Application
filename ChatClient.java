import java.net.*; 
import java.io.*; 

public class ChatServerThread extends Thread { 
    private ChatServer server = null;     
    private Socket socket = null;     
    private DataInputStream in = null;     
    private PrintStream out = null; 
    private int ID = -1;     

    public ChatServerThread(ChatServer serv, Socket sock) {         
        super();         
        this.server = serv;         
        this.socket = sock;         
        this.ID = socket.getPort(); 
    } 

    public void send(String msg) { 
        out.println(msg); 
        out.flush(); 
    }     

    public int getID() {         
        return ID; 
    } 

    public void run() { 
        System.out.println("Server thread " + ID + " running");         
        while (true) { 
            try { 
                server.handle(ID, in.readLine()); 
            } catch (IOException e) { 
                System.out.println(ID + " error reading: " + e.getMessage()); 
                server.remove(ID); 
                stop(); 
            } 
        } 
    } 

    public void open() throws IOException {         
        in = new DataInputStream(socket.getInputStream()); 
        out = new PrintStream(socket.getOutputStream()); 
    } 

    public void close() throws IOException { 
        if (socket != null) 
            socket.close();         
        if (in != null) 
            in.close();         
        if (out != null) 
            out.close(); 
    } 
}
