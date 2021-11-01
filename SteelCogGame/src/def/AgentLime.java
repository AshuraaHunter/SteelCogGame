package def;

public class AgentLime extends Sprite {
	private boolean canMove;
	
	public AgentLime() {
		super(16,16,"AgentLime.png");
	}
	
	public boolean getCanMove() {
		return canMove;
	}
	
	public void setCanMove(boolean temp) {
		this.canMove = temp;
	}
}
