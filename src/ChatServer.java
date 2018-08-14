import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.*;

public class ChatServer {
    boolean started = false;

    ServerSocket ss = null;

    static List<Client> clients = new ArrayList<Client>();

    public static void main(String[] args) {

        new ChatServer().start();
        //based on TCP, and start a server, and accept the connection from client;
    }

    static class Client implements Runnable { // represent the package of client on server;
        private Socket s; // represent the connection between the
        private DataInputStream dis = null;
        private DataOutputStream dos = null;
        private boolean bConnected = false;



        public Client(Socket s) {
            this.s = s;
            try {
                dis = new DataInputStream(s.getInputStream());
                dos = new DataOutputStream(s.getOutputStream());
                bConnected = true;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public void send(String str) throws IOException {
            try {
                dos.writeUTF(str);
            } catch (IOException e) {
                clients.remove(this);
                System.out.println("the opposite exit, and I exclude it");
                //e.printStackTrace();
            }

        }

        public void run() {
            try {
                while (bConnected) {
                    String str = dis.readUTF();
                    System.out.println(str);
                    for (int i = 0; i < clients.size(); i++) {
                        Client c = clients.get(i);
                        c.send(str);
                        //System.out.println(" a string send !");
                    }
                }

            } catch (EOFException e) {
                    System.out.println("Client disconnected");
            } catch (IOException e) {
                    e.printStackTrace();
            } finally {
                    try {
                        if(dis!= null) dis.close();
                        if(dos!= null) dos.close();
                        if(s != null) {
                            s.close();
                            //s = null; // it will be recycled;
                        }

                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }

                    if(this != null) {
                        clients.remove(this);
                    }
            }

        }

    }

    public void start() {
        try {
            ss = new ServerSocket(8888);//already in use;
            started = true;
        } catch(BindException e) {
            System.out.println("port already in use.....");
            System.out.println("close and restart");
            System.exit(0);
        } catch (IOException e) {
            e.printStackTrace();
        }


        try{

            // use one Thread to control one client;
            // main Thread only control the connection of client;
            // other Thread used to communication of server;
            while(started) {

                Socket s = ss.accept(); // accept the connection from client;

                Client c = new Client(s);
                System.out.println("a client connected");
                new Thread(c).start();
                clients.add(c);
                //dis.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                ss.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
