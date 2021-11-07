package def;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class SteelCog extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8926652988288523971L;
	
	private AgentLime myAgentLime;
	private Wall[] myWall = new Wall[6];
	private Finish myFinish;
	
	private JLabel AgentLimeLabel, FinishLabel, TimeLabel;
	private JLabel[] WallLabel = new JLabel[6];
	private ImageIcon AgentLimeImage, WallImage, LavaWallImage, FinishImage;
	
	private Timer time;
	private int timeLeft = 99;
	private final int timePause = 1000; // delay in milliseconds before timer start
	private final int timeInterval = 1000; // delay in milliseconds for iteration of timer
	
	private Container content;
	
	public SteelCog() {
		super("Steel Cog"); // window title
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		
		myAgentLime = new AgentLime();
		AgentLimeLabel = new JLabel();
		AgentLimeImage = new ImageIcon(getClass().getResource(myAgentLime.getFilename()));
		AgentLimeLabel.setIcon(AgentLimeImage);
		AgentLimeLabel.setSize(myAgentLime.getWidth(), myAgentLime.getHeight());
		
		for(int i=0; i<myWall.length && i<WallLabel.length; i++) {
			  myWall[i] = new Wall();
			  WallLabel[i] = new JLabel();
		}
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
		add(FinishLabel);
		
		TimeLabel = new JLabel();
		add(TimeLabel);
		TimeLabel.setForeground(Color.white);
		TimeLabel.setFont(new Font("Power Red and Green",Font.BOLD,40));
		TimeLabel.setSize(72,36);
		TimeLabel.setLocation((int)(GameProperties.SCREEN_WIDTH*.46),10);
		
		time = new Timer();
		TimeLabel.setText(Integer.toString(timeLeft));
        time.scheduleAtFixedRate(new TimerTask() {
            public void run() {
                if (timeLeft == 1) {
                    timeLeft--;
                    TimeLabel.setText(Integer.toString(timeLeft));
                    time.cancel();
                    // game over
                } else {
                    timeLeft--;
                    TimeLabel.setText(Integer.toString(timeLeft));
                }
            }
        }, timePause, timeInterval);
		
		AgentLimeLabel.setLocation(myAgentLime.getX(), myAgentLime.getY());
		for (int i = 0; i < WallLabel.length; i++) {
			WallLabel[i].setLocation((50*(i+1)), (50*(i+1))); // work on positioning logic
		}
		FinishLabel.setLocation(myFinish.getX(), myFinish.getY());
		
		content.addKeyListener(this);
		content.setFocusable(true);
		
		myAgentLime.setCanMove(true);
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
		
		// need to setup collision down here (i.e. looping through each wall to check for overlap)
		// not sure how to do finish collision yet
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			for (int i = 0; i < WallLabel.length; i++) {
				if (ax + myAgentLime.getWidth() > WallLabel[i].getX() 
						&& ax < WallLabel[i].getX() + WallLabel[i].getWidth() 
						&& ay + myAgentLime.getHeight() - GameProperties.CHARACTER_STEP > WallLabel[i].getY() 
						&& ay - GameProperties.CHARACTER_STEP < WallLabel[i].getY() + WallLabel[i].getHeight()) {
					myAgentLime.setCanMove(false);
				}
			}
			if (myAgentLime.getCanMove()==true) {
				ay -= GameProperties.CHARACTER_STEP;
				if (ay + myAgentLime.getHeight() < 0) {
					ay = GameProperties.SCREEN_HEIGHT;
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			for (int i = 0; i < WallLabel.length; i++) {
				if (ax + myAgentLime.getWidth() > WallLabel[i].getX() 
						&& ax < WallLabel[i].getX() + WallLabel[i].getWidth() 
						&& ay + myAgentLime.getHeight() + GameProperties.CHARACTER_STEP > WallLabel[i].getY() 
						&& ay + GameProperties.CHARACTER_STEP < WallLabel[i].getY() + WallLabel[i].getHeight()) {
					myAgentLime.setCanMove(false);
				}
			}
			if (myAgentLime.getCanMove()==true) {
				ay += GameProperties.CHARACTER_STEP;
				if (ay > GameProperties.SCREEN_HEIGHT) {
					ay = -1 * myAgentLime.getHeight();
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			for (int i = 0; i < WallLabel.length; i++) {
				if (ax + myAgentLime.getWidth() - GameProperties.CHARACTER_STEP > WallLabel[i].getX() 
						&& ax - GameProperties.CHARACTER_STEP < WallLabel[i].getX() + WallLabel[i].getWidth() 
						&& ay + myAgentLime.getHeight() > WallLabel[i].getY() 
						&& ay < WallLabel[i].getY() + WallLabel[i].getHeight()) {
					myAgentLime.setCanMove(false);
				}
			} if (myAgentLime.getCanMove()==true) {
				ax -= GameProperties.CHARACTER_STEP;
				if (ax + myAgentLime.getWidth() < 0) {
					ax = GameProperties.SCREEN_WIDTH;
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			for (int i = 0; i < WallLabel.length; i++) {
				if (ax + myAgentLime.getWidth() + GameProperties.CHARACTER_STEP > WallLabel[i].getX() 
						&& ax + GameProperties.CHARACTER_STEP < WallLabel[i].getX() + WallLabel[i].getWidth() 
						&& ay + myAgentLime.getHeight() > WallLabel[i].getY() 
						&& ay < WallLabel[i].getY() + WallLabel[i].getHeight()) {
					myAgentLime.setCanMove(false);
				}
			} if (myAgentLime.getCanMove()==true) {
				ax += GameProperties.CHARACTER_STEP;
				if (ax > GameProperties.SCREEN_WIDTH) {
					ax = -1 * myAgentLime.getWidth();
				}
			}
		}
		
		myAgentLime.setX(ax);
		myAgentLime.setY(ay);
		
		AgentLimeLabel.setLocation(myAgentLime.getX(), myAgentLime.getY());
		myAgentLime.setCanMove(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
}
