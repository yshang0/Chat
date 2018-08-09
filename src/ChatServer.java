import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {

    public static void main(String[] args) {
        try {
            ServerSocket ss = new ServerSocket(8888);
            while(true) {
                Socket s = ss.accept(); // accept the connection from client;
                System.out.println("a client connected");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        //based on TCP, and start a server, and accept the connection from client;
    }
}
