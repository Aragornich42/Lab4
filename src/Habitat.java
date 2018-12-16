import javax.imageio.ImageIO;
import javax.swing.*;
import java.applet.Applet;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.util.regex.Pattern;

public class Habitat extends Applet{
	
	private static final long serialVersionUID = 3463300763713951195L;
	
	private Timer timer = new Timer();
	private double time = 0;
	private Random rnd = new Random();
	private LinkedList<Developer> devs = new LinkedList<>();
	private LinkedList<Manager> manags = new LinkedList<>();
	private HashMap<Integer, Double> devs_mp = new HashMap<>();
	private HashMap<Integer, Double> manags_mp = new HashMap<>();
	private boolean run_via_frm = false;
	private Image off_scrn_img;
	private boolean timerhidden = false;
	private boolean active = true;
	private Image dev_img;
	private Image manag_img;
	private int n1 = rnd.nextInt(5) + 5;
	private int n2 = rnd.nextInt(3) + 7;
	private int k = rnd.nextInt(9) + 1;
	private int p = rnd.nextInt(40) + 60;
	private int speed = rnd.nextInt(10) + 10;
	private int radius = rnd.nextInt(10) + 5;
	private MyConsole console;
	private String[] pars;
    private String info1 = "";
    private String info2 = "";
    private File f;
    private FileWriter fw;
    private File devSer;
    private File manSer;
    private ObjectOutputStream oosdev;
    private ObjectOutputStream oosman;
    private ObjectInputStream oisdev;
    private ObjectInputStream oisman;
	private JFileChooser jfc = new JFileChooser();
    private JFrame frame = new JFrame();

	private class Task extends TimerTask {
		private Habitat hbt;
		private boolean first_rn = true;
		private long start_tm = 0;
		private long end_tm = 0;
		
		Task(Habitat exmpl) {
			hbt = exmpl;
		}
		
		public void run() {
			if (first_rn) {
				start_tm = System.currentTimeMillis();
				end_tm = start_tm;
				first_rn = false;
			}
			long current_tm = System.currentTimeMillis();
			double elapsed = (current_tm - start_tm) / 1000.0;
			double frame_tm = (end_tm - start_tm) / 1000.0;
			hbt.Update(elapsed);
			end_tm = current_tm;
		}
	}
	
	private Task tsk = new Task(this);
	
	public Habitat() throws IOException {
		KeyAdapter pk;
		pk = new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				int keyCode = e.getKeyCode();
				System.out.println(e);
				switch(keyCode) {
					case KeyEvent.VK_B:
						timer.schedule(tsk, 0, 1000);
						repaint();
						break;
					case KeyEvent.VK_E:
						timer.cancel();
						active = false;
						for(int i = 1; i <= devs_mp.size(); i++) {
							System.out.println("Разработчик "+ i +", время рождения: " +
									devs_mp.get(i));
						}
						for(int i = 1; i <= manags_mp.size(); i++) {
							System.out.println("Менеджер "+ i + ", время рождения: " +
									manags_mp.get(i));
						}
						repaint();
						break;
					case KeyEvent.VK_T:
						timerhidden = !timerhidden;
						repaint();
						break;
					case KeyEvent.VK_S:
						try {
							Thread.sleep(5000);
						} catch (InterruptedException e1) {
							e1.printStackTrace();
						}
							repaint();
							break;
					case KeyEvent.VK_C:
						console = new MyConsole();
						break;
					case KeyEvent.VK_Z:
						try {
							jfc.showOpenDialog(frame);
							devSer = jfc.getSelectedFile();
							oosdev = new ObjectOutputStream(new FileOutputStream(devSer));
							oosdev.writeObject(devs);
							oosdev.close();
							jfc.showOpenDialog(frame);
							manSer = jfc.getSelectedFile();
							oosman = new ObjectOutputStream(new FileOutputStream(manSer));
							oosman.writeObject(manags);
							oosdev.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						break;
					case KeyEvent.VK_X:
						try {
							jfc.showOpenDialog(frame);
							oisdev = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
							jfc.showOpenDialog(frame);
							oisman = new ObjectInputStream(new FileInputStream(jfc.getSelectedFile()));
							LinkedList<Developer> devs2 = (LinkedList<Developer>) oisdev.readObject();
							LinkedList<Manager> mans2 = (LinkedList<Manager>) oisman.readObject();
							for(Developer dev : devs2)
								devs.add(dev);
							oisdev.close();
							for(Manager man : mans2)
								manags.add(man);
							oisman.close();
						} catch (IOException e1) {
							e1.printStackTrace();
						} catch (ClassNotFoundException e1) {
							e1.printStackTrace();
						}
						break;
				}
			}
		};
		this.addKeyListener(pk);
		Init();
	}
	
	public Habitat(boolean via_frm) throws IOException {
		run_via_frm = via_frm;
		Init();
	}
	
	private void Update(double elapsed_tm) {
		time = elapsed_tm;
		int tim = (int)elapsed_tm;
		float p1 = rnd.nextInt(100);
		boolean bool = false;
		String[] cmd2;
		
		
		
		if(tim % n1 == 0 && p1 <= p) {
			devs.add(new Developer(rnd.nextInt(800), rnd.nextInt(500), speed));
			devs_mp.put(devs.size(), time);
			if(devs.size() != 0)
				bool = (manags.size() / devs.size() * 100) < k;
			else
				bool = false;
		}
		
		if(tim % n2 == 0 && bool) {
			manags.add(new Manager(rnd.nextInt(800), rnd.nextInt(500), radius, speed));
			manags_mp.put(manags.size(), time);
		}

		if(console != null) {
            info1 = console.getCurinfo();
            if(!info1.equals(info2)) {
                pars = Parser(info1);
                if (pars[pars.length - 1].equals("Lay")) {
                    manags.clear();
                    console.addInfo("Managers layed off\n");
                } else {
                    cmd2 = pars[pars.length - 1].split(Pattern.quote(" "));
                    for (int i = 0; i < Integer.parseInt(cmd2[1]); i++)
                        manags.add(new Manager(rnd.nextInt(800), rnd.nextInt(500), radius, speed));
                    console.addInfo("Managers hired\n");
                }
                info1 = console.getCurinfo();
                info2 = info1;
            }
        }

		this.repaint();
		Toolkit.getDefaultToolkit().sync();
	}
	
	public void init() {
		resize(1300, 650);
		try {
			dev_img = ImageIO.read(this.getClass().getResource("1.png"));
			manag_img = ImageIO.read(this.getClass().getResource("2.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(n1 + " " + n2 + " " + k + " " + p);
	}
	
	private void Init() {	}
	
	public void paint(Graphics g) {
		int width = getSize().width;
		int height = getSize().height;
		off_scrn_img = createImage(width, height);
		Graphics offScreenGraphics = off_scrn_img.getGraphics();
		
		if(active) {
			offScreenGraphics.setColor(Color.WHITE);
			offScreenGraphics.fillRect(0, 0, this.getWidth(), this.getHeight());
			offScreenGraphics.setColor(Color.BLACK);
			String str = "Time = " + time;
			
			if(timerhidden) {
				offScreenGraphics.drawString(str, 1200, 15);
			}

			for (Developer dev : devs) {
				Thread develops = new Thread(dev);
				develops.start();
				if (dev.getX() < 1300) {
					offScreenGraphics.drawImage(dev_img, dev.getX(),
							dev.getY(), this);
				}
			}

			for (Manager manag : manags) {
				Thread managers = new Thread(manag);
				managers.start();
				if (manag.getX() > 0) {
					offScreenGraphics.drawImage(manag_img, manag.getX(),
							manag.getY(), this);
				}
			}
		} else {
			String str = "Время симуляции: " + time;
			String dcount = "Разработчиков создано: " + devs.size();
			String mcount = "Менеджеров создано: " + manags.size();
			offScreenGraphics.setColor(Color.GREEN);
			offScreenGraphics.setFont(new Font("Helvetica", Font.ITALIC, 22));
			offScreenGraphics.drawString(str, 15, 25);
			offScreenGraphics.setColor(Color.PINK);
			offScreenGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			offScreenGraphics.drawString(dcount, 15, 45);
			offScreenGraphics.setColor(Color.BLUE);
			offScreenGraphics.setFont(new Font("TimesRoman", Font.PLAIN, 14));
			offScreenGraphics.drawString(mcount, 15, 65);
			jfc.showSaveDialog(frame);
			f = jfc.getSelectedFile();
			try {
                fw = new FileWriter(f);
				fw.write(str + "\n");
				fw.write(dcount + "\n");
				fw.write(mcount + "\n");
				fw.write("Период времени создания разработчика: " + n1 + " сек.\n");
				fw.write("Период времени создания менеджера: " + n2 + " сек.\n");
				fw.write("Вероятность создания разработчика: " + p + "%\n");
				fw.write("Процент менеджеров: " + k + "%\n");
				fw.write("Скорость движения объектов: " + speed + "\n");
				fw.close();
				System.out.println("Запись в файл проведена успешно.");
			} catch (IOException e) {
				e.printStackTrace();
			}
			devs.clear();
			manags.clear();
		}
		g.drawImage(off_scrn_img, 0, 0, this);
	}
	
	public void update(Graphics g) {
		paint(g);
	}

	private String[] Parser(String cmd) {
	    return cmd.split(Pattern.quote("\n"));
    }

}
