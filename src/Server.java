import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.util.Scanner;

public class Server {

    public static void main(String[] args){
        try {
            System.out.println("Server is running");
            int port = 8080;
            //создаем сокет сервера
            ServerSocket ss = new ServerSocket(port);
            //подключаем клиентов
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
            //ждем команды перезагрузки
            while(true) {
                outStream.writeInt(command);
                if (command == 1) {
                    //команда обмена
                    System.out.println("was:");
                    System.out.println(inStream.readInt() + " developers");
                    System.out.println(inStream.readInt() + " managers");
                    System.out.println("now:");
                    System.out.println(inStream.readInt() + " developers");
                    System.out.println(inStream.readInt() + " managers");
                    command = 0;
                } else if (command == 2) {
                    System.out.println("Drop session...");
                    //команда перезагрузки
                    outStream.writeInt(command);
                    byte[] data = {2};
                    InetAddress addr = InetAddress.getByName(sock.getInetAddress().getCanonicalHostName());
                    DatagramPacket pack = new DatagramPacket(data, data.length, addr, 3333);
                    DatagramSocket ds = new DatagramSocket();
                    ds.send(pack);
                    ds.close();
                    //закрытие всего
                    scanner.close();
                    inStream.close();
                    outStream.close();
                    sock.close();
                    System.out.println("Ready!");
                    break;
                } else continue;
            }
        }
        catch(Exception e) { e.printStackTrace(); }
    }

}
