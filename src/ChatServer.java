import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChatServer {



    public static void main(String[] args) {
        boolean started = false;
        Socket s = null;
        ServerSocket ss = null;
        DataInputStream dis = null;
        try {
            ss = new ServerSocket(8888);//already in use;
        } catch(BindException e) {
            System.out.println("port already in use.....");
            System.out.println("close and restart");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try{
            started = true;
            while(started) {
                boolean bConnected = false;

                s = ss.accept(); // accept the connection from client;
                System.out.println("a client connected");
                bConnected = true;

                dis = new DataInputStream(s.getInputStream());
                while(bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                }
                //dis.close();
            }
        } catch (EOFException e) {
            System.out.println("Client connected");
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if(dis!= null) dis.close();
                if(s!= null) s.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }


        //based on TCP, and start a server, and accept the connection from client;
    }
}
