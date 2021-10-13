package def;

import java.awt.Rectangle;

/* 
 * Basic character class => width, height, x/y, filename
 */
public class Sprite {
	// define data members
	protected int x, y; // position (origin is upper left)
	protected int height, width; // size
	protected String filename; // sprite location
	protected Rectangle r;
	protected Boolean visible;
	
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x; this.r.setLocation(this.x,this.y);
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y; this.r.setLocation(this.x,this.y);
	}
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height; this.r.setSize(this.width,this.height);
	}
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width; this.r.setSize(this.width,this.height);
	}
	public String getFilename() {
		return filename;
	}
	public void setFilename(String filename) {
		this.filename = filename;
	}
	public Rectangle getRectangle() {
		return this.r;
	}
	public Boolean getVisible() {
		return visible;
	}
	public void setVisible(Boolean visible) {
		this.visible = visible;
	}
	// default
	public Sprite() {
		super();
		this.x = 0; this.y = 0;
		this.height = 0; this.width = 0;
		this.filename = "";
		this.r = new Rectangle(this.x,this.y,this.width,this.height);
		this.visible = true;
	}
	public Sprite(int x, int y, int height, int width, String filename) {
		super();
		this.x = x;
		this.y = y;
		this.height = height;
		this.width = width;
		this.filename = filename;
		this.r = new Rectangle(this.x,this.y,this.width,this.height);
		this.visible = true;
	}
	public Sprite(int width, int height, String filename) {
		super();
		this.x = 0; this.y = 0;
		this.height = height;
		this.width = width;
		this.filename = filename;
		this.r = new Rectangle(this.x,this.y,this.width,this.height);
		this.visible = true;
	}
	
	public void Display( ) {
		System.out.println("x,y:"+this.x+","+this.y);
	}
}
