package def;

import java.awt.Color;
import java.awt.Container;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SteelCog extends JFrame implements KeyListener {
	private AgentLime myAgentLime;
	private Wall[] myWall;
	private LavaWall myLavaWall;
	private Finish myFinish;
	
	private JLabel AgentLimeLabel, FinishLabel;
	private JLabel[] WallLabel, LavaWallLabel;
	private ImageIcon AgentLimeImage, WallImage, LavaWallImage, FinishImage;
	
	private Container content;
	
	public SteelCog() {
		super("Steel Cog"); // window title
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		
		myAgentLime = new AgentLime();
		AgentLimeLabel = new JLabel();
		AgentLimeImage = new ImageIcon(getClass().getResource(myAgentLime.getFilename()));
		AgentLimeLabel.setIcon(AgentLimeImage);
		AgentLimeLabel.setSize(myAgentLime.getWidth(), myAgentLime.getHeight());
		
		myWall = new Wall[6];
		WallLabel = new JLabel[6];
		WallImage = new ImageIcon(getClass().getResource(myWall[0].getFilename()));
		for (int i = 0; i < WallLabel.length; i++) {
			WallLabel[i].setIcon(WallImage);
			if (i % 2 == 0) {
				WallLabel[i].setSize(100, 200);
			} else {
				WallLabel[i].setSize(200, 100);
			}
		}
		
		myFinish = new Finish();
		FinishLabel = new JLabel();
		FinishImage = new ImageIcon(getClass().getResource(myFinish.getFilename()));
		FinishLabel.setIcon(FinishImage);
		FinishLabel.setSize(myFinish.getWidth(), myFinish.getHeight());
		
		content = getContentPane();
		content.setBackground(Color.black);
		setLayout(null);
		
		myAgentLime.setX(100);
		myAgentLime.setY(500);
		
		myFinish.setX(700);
		myFinish.setY(50);
		
		add(AgentLimeLabel);
		for (int i = 0; i < WallLabel.length; i++) {
			add(WallLabel[i]);
		}
		
		AgentLimeLabel.setLocation(myAgentLime.getX(), myAgentLime.getY());
		for (int i = 0; i < WallLabel.length; i++) {
			WallLabel[i].setLocation((25*(i+1)), (25*(i+1)));
		}
		FinishLabel.setLocation(myFinish.getX(), myFinish.getY());
		
		content.addKeyListener(this);
		content.setFocusable(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void main(String[] args) {
		SteelCog myGame = new SteelCog();
		myGame.setVisible(true);
	}

	@Override
	public void keyTyped(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int ax = myAgentLime.getX();
		int ay = myAgentLime.getY();
		
		// includes checking for passing boundaries
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			ay -= GameProperties.CHARACTER_STEP;
			if (ay + myAgentLime.getHeight() < 0) {
				ay = GameProperties.SCREEN_HEIGHT;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			ay += GameProperties.CHARACTER_STEP;
			if (ay > GameProperties.SCREEN_HEIGHT) {
				ay = -1 * myAgentLime.getHeight();
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			ax -= GameProperties.CHARACTER_STEP;
			if (ax + myAgentLime.getWidth() < 0) {
				ax = GameProperties.SCREEN_WIDTH;
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			ax += GameProperties.CHARACTER_STEP;
			if (ax > GameProperties.SCREEN_WIDTH) {
				ax = -1 * myAgentLime.getWidth();
			}
		}
		
		myAgentLime.setX(ax);
		myAgentLime.setY(ay);
		
		AgentLimeLabel.setLocation(myAgentLime.getX(), myAgentLime.getY());
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}