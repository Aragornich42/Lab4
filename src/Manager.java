import java.io.Serializable;
import java.util.Random;

public class Manager extends Men implements IBehavior, Runnable, Serializable {
	
	private Random rnd = new Random();
	private int direct = rnd.nextInt(8);
	
	private int radius;
	private int x0;
	private int y0;
	
	public int getRadius() {
		return radius;
	}

	public void setRadius(int radius) {
		this.radius = radius;
	}

	public Manager(int x, int y, int rad, int speed) {
		super(x, y, speed);
		radius = rad;
		y0 = y + radius;
		x0 = x;
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
