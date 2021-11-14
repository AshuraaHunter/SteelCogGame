package def;

public class LavaWallStopBtn extends Sprite {
	private boolean isOn, wasOn;
	
	public Boolean getIsOn() {
		return isOn;
	}
	public void setIsOn(Boolean temp) {
		this.isOn = temp;
	}
	public boolean getWasOn() {
		return wasOn;
	}
	public void setWasOn(boolean wasOn) {
		this.wasOn = wasOn;
	}
	
	public LavaWallStopBtn() {
		super(25,25,"lavaWallStopBtn.png");
		this.isOn = false;
		this.wasOn = false;
	}
}
