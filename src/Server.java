import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

public class Server {

    public static void main(String[] args){
        try {
            System.out.println("Server is running");
            int port = 8080;

            ServerSocket ss = new ServerSocket(port);

            while (true) {
                Socket s = ss.accept();
                ServerConnectionProcessor p = new ServerConnectionProcessor(s);
                p.start();
            }
        }
        catch(Exception e) { e.printStackTrace(); }

    }
}
class ServerConnectionProcessor extends Thread {
    private Socket sock;
    public ServerConnectionProcessor(Socket s) {
        sock = s;
    }
    public void run() {
        try {
            DataInputStream inStream = new DataInputStream(sock.getInputStream());
            DataOutputStream outStream = new DataOutputStream(sock.getOutputStream());
            Scanner scanner = new Scanner(System.in);
            System.out.println("\n1 - change; 2 - reload.");
            int command = scanner.nextInt();

            while(true) {
                outStream.writeInt(command);
                if (command == 1) {
                    System.out.println("was:");
                    System.out.println(inStream.readInt() + " developers");
                    System.out.println(inStream.readInt() + " managers");
                    System.out.println("now:");
                    System.out.println(inStream.readInt() + " developers");
                    System.out.println(inStream.readInt() + " managers");
                    command = 0;
                } else if (command == 2) {
                    System.out.println("Drop session...");
                    inStream.close();
                    outStream.close();
                    sock.close();
                    System.out.println("Ready!");
                    break;
                } else continue;
            }

            /*scanner.close();
            inStream.close();
            outStream.close();
            sock.close();*/
        }
        catch(Exception e) { /*e.printStackTrace();*/ }
    }

}
