import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.*;

public class MyConsole extends JFrame {

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
                    curinfo = area.getText();
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });
    }

    public String getCurinfo() {
        return curinfo;
    }

    public String curinfo = "";

    public void addInfo(String info) {
        area.setText(area.getText() + info);
    }

}
