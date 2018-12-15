import java.io.Serializable;

abstract class Men implements Serializable {
	
	int x;
	int y;
	int speed;
	
	public int getSpeed() {
		return speed;
	}

	public void setSpeed(int speed) {
		this.speed = speed;
	}

	Men(int X, int Y, int Speed) {
		super();
		x = X;
		y = Y;
		speed = Speed;
	}
	
	private Men() {
		x = 10;
		y = 10;
		speed = 10;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
}
