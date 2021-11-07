package def;

public class AgentLime extends Sprite {
	private boolean canMove, isAlive;
	
	public AgentLime() {
		super(16,16,"AgentLime.png");
		this.canMove = true; this.isAlive = true;
	}
	
	public boolean getCanMove() {
		return canMove;
	}
	
	public void setCanMove(boolean temp) {
		this.canMove = temp;
	}
	
	public boolean getIsAlive() {
		return isAlive;
	}
	
	public void setIsAlive(boolean temp) {
		this.isAlive = temp;
	}
}
