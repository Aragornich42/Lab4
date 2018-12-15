import java.io.Serializable;
import java.util.Random;

public class Developer extends Men implements IBehavior, Runnable, Serializable {
	
	private Random rnd = new Random();
	private int direct = rnd.nextInt(8);
	
	public Developer(int x, int y, int speed) {
		super(x, y, speed);
	}
	
	@Override
	public void move() {
		switch(direct) {
		case 0:
			y -= speed;
			break;
		case 1:
			x += speed;
			y -= speed;
			break;
		case 2:
			x += speed;
			break;
		case 3:
			x += speed;
			y += speed;
			break;
		case 4:
			y += speed;
			break;
		case 5:
			x -= speed;
			y += speed;
			break;
		case 6:
			x -= speed;
			break;
		case 7:
			x -= speed;
			y -= speed;
			break;
		}
		direct = rnd.nextInt(8);
	}
	
	@Override
	public void run() {
		move();
	}
}
