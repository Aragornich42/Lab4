import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class MyConsole extends JFrame {

    /*DataInputStream dis = new DataInputStream(new InputStream() {
        @Override
        public int read() throws IOException { return 0; }
    });

    DataOutputStream dos = new DataOutputStream(new OutputStream() {
        @Override
        public void write(int b) throws IOException { }
    });*/

    JTextArea area = new JTextArea( 28, 50);
    JPanel panel = new JPanel();

    public MyConsole() {
        super("MyConsole");
        panel.add(new JScrollPane(area));
        setContentPane(panel);
        setSize(600, 500);
        setVisible(true);

        area.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER) {
                    /*try {
                        dos.writeUTF(area.getText());
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }*/
                    curinfo = area.getText();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }

    /*public void run() {
        String tmp;
        try {
            while(true) {
                if (dis.available() > 0)
                    if ((tmp = dis.readUTF()).equals("End")) {
                        break;
                    } else {
                        area.setText(area.getText() + tmp + "\n");
                    }
            }
            dis.close();
            dos.close();
        } catch (IOException e){
            e.printStackTrace();
        }
    }*/

    public String getCurinfo() {
        return curinfo;
    }

    public String curinfo = "";

    public void addInfo(String info) {
        area.setText(area.getText() + info);
    }

}
