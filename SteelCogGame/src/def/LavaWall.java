package def;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class LavaWall extends Sprite implements Runnable {
	private Boolean moving;
	private int direction;
	private Thread t;
	private JLabel AgentLimeLabel, LavaWallLabel;
	private AgentLime myAgentLime;
	
	public Boolean getMoving() {
		return moving;
	}

	public void setMoving(Boolean temp) {
		this.moving = temp;
	}
	
	public int getDirection() {
		return direction;
	}
	
	public void setDirection(int temp) {
		this.direction = temp;
	}
	
	public void setAgentLime(AgentLime temp) {
		this.myAgentLime = temp;
	}
		
	public void setLavaWallLabel(JLabel temp) {
		this.LavaWallLabel = temp;
	}
	
	public void setAgentLimeLabel(JLabel temp) {
		this.AgentLimeLabel = temp;
	}
	
	public LavaWall() {
		super(67,200,"lavaWall.png");
		this.moving = true;
		this.direction = 0;
	}
	
	public LavaWall(JLabel temp) {
		super(67,200,"lavaWall.png");
		this.moving = true;
		this.direction = 0;
		this.LavaWallLabel = temp;
	}
	
	public void move() {
		t = new Thread(this,"LavaWallThread");
		t.start();
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		LavaWallLabel.setIcon(new ImageIcon(getClass().getResource("lavaWall.png")));
		AgentLimeLabel.setIcon(new ImageIcon(getClass().getResource("AgentLime.png")));
		
		while (moving) {
			int tx = this.x;
			int ty = this.y;
			
			if (this.direction==0) {
				ty += 4;
			} else {
				ty -= 4;
			}
			
			if (ty+this.height >= (GameProperties.SCREEN_HEIGHT)-50) {
				this.direction = 1;
			} else if (ty <= 20) {
				this.direction = 0;
			}
			
			setX(tx);
			setY(ty);
			
			LavaWallLabel.setLocation(this.x, this.y);
			this.detectCollision();
			
			try {
				Thread.sleep(50);
			} catch (Exception e) {
				
			}
		}
	}
	
	private void detectCollision() {
		if (this.r.intersects(myAgentLime.getRectangle())) {
			this.moving = false;
			myAgentLime.setIsAlive(false);
		}
	}
}
