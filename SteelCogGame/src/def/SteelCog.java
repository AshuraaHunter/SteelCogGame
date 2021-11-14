package def;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class SteelCog extends JFrame implements KeyListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 8926652988288523971L;
	
	private AgentLime myAgentLime;
	private Wall[] myWall = new Wall[6];
	private LavaWall[] myLavaWall = new LavaWall[3];
	private Finish myFinish;
	private Box myBox;
	private Goal myGoal;
	private LavaWallStopBtn myStopBtn;
	private FinishBtn myFinishBtn;
	
	private JLabel AgentLimeLabel, FinishLabel, BoxLabel, GoalLabel, StopBtnLabel, FinishBtnLabel, TimeLabel;
	private JLabel[] WallLabel = new JLabel[6];
	private JLabel[] LavaWallLabel = new JLabel[3];
	private ImageIcon AgentLimeImage, WallImage, LavaWallImage, FinishImage, BoxImage, GoalImage, StopBtnImage, FinishBtnImage;
	
	private int[] xArray = new int[] {100,-100,560,40,560,590};
	private int[] yArray = new int[] {370,370,400,40,0,100};
	
	private int score = 10000;
	private int dimensionCnt = 0; // tracks the amount of times the player has phased across dimensions
	private int stepCnt = 0; // tracks steps taken
	
	private String name;
	
	private Timer time, gameCheck;
	private int timeLeft = 99;
	private final int timePause = 1000; // delay in milliseconds before timer start
	private final int timeInterval = 1000; // delay in milliseconds for iteration of timer
	
	private boolean won = false;
	
	private Container content;
	private PopUpMessage popup;
	private GameSQLite database;
	
	public SteelCog() {
		super("Steel Cog"); // window title
		setSize(GameProperties.SCREEN_WIDTH, GameProperties.SCREEN_HEIGHT);
		
		name = JOptionPane.showInputDialog("Please enter your name.");
		
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
		
		for(int i=0; i<myLavaWall.length && i<LavaWallLabel.length; i++) {
			  myLavaWall[i] = new LavaWall();
			  LavaWallLabel[i] = new JLabel();
			  myLavaWall[i].setLavaWallLabel(LavaWallLabel[i]);
			  myLavaWall[i].setAgentLime(myAgentLime);
			  myLavaWall[i].setAgentLimeLabel(AgentLimeLabel);
		}
		LavaWallImage = new ImageIcon(getClass().getResource(myLavaWall[0].getFilename()));
		for (int i = 0; i < LavaWallLabel.length; i++) {
			LavaWallLabel[i].setIcon(LavaWallImage);
			LavaWallLabel[i].setSize(myLavaWall[i].getWidth(),myLavaWall[i].getHeight());
		}
		
		myFinish = new Finish();
		FinishLabel = new JLabel();
		FinishImage = new ImageIcon(getClass().getResource(myFinish.getFilename()));
		FinishLabel.setIcon(FinishImage);
		FinishLabel.setSize(myFinish.getWidth(), myFinish.getHeight());
		
		myBox = new Box();
		BoxLabel = new JLabel();
		BoxImage = new ImageIcon(getClass().getResource(myBox.getFilename()));
		BoxLabel.setIcon(BoxImage);
		BoxLabel.setSize(myBox.getWidth(), myBox.getHeight());
		
		myGoal = new Goal();
		GoalLabel = new JLabel();
		GoalImage = new ImageIcon(getClass().getResource(myGoal.getFilename()));
		GoalLabel.setIcon(GoalImage);
		GoalLabel.setSize(myGoal.getWidth(), myGoal.getHeight());
		
		myStopBtn = new LavaWallStopBtn();
		StopBtnLabel = new JLabel();
		StopBtnImage = new ImageIcon(getClass().getResource(myStopBtn.getFilename()));
		StopBtnLabel.setIcon(StopBtnImage);
		StopBtnLabel.setSize(myStopBtn.getWidth(), myStopBtn.getHeight());
		
		myFinishBtn = new FinishBtn();
		FinishBtnLabel = new JLabel();
		FinishBtnImage = new ImageIcon(getClass().getResource(myFinishBtn.getFilename()));
		FinishBtnLabel.setIcon(FinishBtnImage);
		FinishBtnLabel.setSize(myFinishBtn.getWidth(), myFinishBtn.getHeight());
		
		TimeLabel = new JLabel();
		TimeLabel.setForeground(Color.white);
		TimeLabel.setFont(new Font("Power Red and Green",Font.BOLD,40));
		TimeLabel.setSize(72,36);
		TimeLabel.setLocation((int)(GameProperties.SCREEN_WIDTH*.46),10);
		
		content = getContentPane();
		content.setBackground(Color.black);
		setLayout(null);
		
		myAgentLime.setX(50);
		myAgentLime.setY(500);
		
		for (int i = 0; i < myWall.length; i++) {
			myWall[i].setX(xArray[i]);
			myWall[i].setY(yArray[i]);
		}
		
		// myFinish.setX(700);
		// myFinish.setY(50);
		myFinish.setX(9999);
		myFinish.setY(9999);
		myFinish.setVisible(false);
		
		myBox.setX(515);
		myBox.setY(340);
		
		myGoal.setX(670);
		myGoal.setY(475);
		
		myStopBtn.setX(368);
		myStopBtn.setY(288);
		
		myFinishBtn.setX(750);
		myFinishBtn.setY(520);
		
		add(AgentLimeLabel);
		for (int i = 0; i < WallLabel.length; i++) {
			add(WallLabel[i]);
		}
		for (int i = 0; i < LavaWallLabel.length; i++) {
			add(LavaWallLabel[i]);
		}
		add(FinishLabel);
		add(BoxLabel);
		add(GoalLabel);
		add(StopBtnLabel);
		add(FinishBtnLabel);
		add(TimeLabel);
		
		time = new Timer();
		gameCheck = new Timer();
		TimeLabel.setText(Integer.toString(timeLeft));
        time.scheduleAtFixedRate(new TimerTask() {
            public void run() {
            	if (timeLeft == 1) {
                    timeLeft--;
                    TimeLabel.setText(Integer.toString(timeLeft));
                    time.cancel();
                    gameCheck.cancel();
                    gameOver(name,score,myAgentLime.getIsAlive(),true);
                } else {
                    timeLeft--;
                    TimeLabel.setText(Integer.toString(timeLeft));
                }
            }
        }, timePause, timeInterval);
        gameCheck.schedule(new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (won==true || !myAgentLime.getIsAlive()) {
            		time.cancel();
            		gameCheck.cancel();
            		myAgentLime.setCanMove(false);
            		for (int i=0; i<myLavaWall.length && i<LavaWallLabel.length; i++) {
            			myLavaWall[i].setMoving(false);
            		}
            		gameOver(name,score,myAgentLime.getIsAlive(),false);
            	}
			}
        },0,1);
		
		AgentLimeLabel.setLocation(myAgentLime.getX(), myAgentLime.getY());
		for (int i = 0; i < WallLabel.length; i++) {
			WallLabel[i].setLocation(myWall[i].getX(),myWall[i].getY()); // work on positioning logic
		}
		for (int i = 0; i < myLavaWall.length; i++) {
			myLavaWall[i].setX((int)((GameProperties.SCREEN_WIDTH*0.32)+(int)(i*(myLavaWall[i].getWidth()+25))));
			myLavaWall[i].setY(50+(i*50));
			myLavaWall[i].move();
		}
		FinishLabel.setLocation(myFinish.getX(), myFinish.getY());
		BoxLabel.setLocation(myBox.getX(), myBox.getY());
		GoalLabel.setLocation(myGoal.getX(), myGoal.getY());
		StopBtnLabel.setLocation(myStopBtn.getX(), myStopBtn.getY());
		FinishBtnLabel.setLocation(myFinishBtn.getX(), myFinishBtn.getY());
		
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
		int bx = myBox.getX();
		int by = myBox.getY();
		
		// need to setup collision down here (i.e. looping through each wall to check for overlap)
		// not sure how to do finish collision yet
		if (!myAgentLime.getIsAlive()) {
			myAgentLime.setCanMove(false);
		}
		if (e.getKeyCode() == KeyEvent.VK_UP) {
			for (int i = 0; i < myWall.length; i++) {
				if (ax + myAgentLime.getWidth() > WallLabel[i].getX() 
						&& ax < WallLabel[i].getX() + WallLabel[i].getWidth() 
						&& ay + myAgentLime.getHeight() - GameProperties.CHARACTER_STEP > WallLabel[i].getY() 
						&& ay - GameProperties.CHARACTER_STEP < WallLabel[i].getY() + WallLabel[i].getHeight()) {
					myAgentLime.setCanMove(false);
				}
			}
			if (myAgentLime.getCanMove()) {
				ay -= GameProperties.CHARACTER_STEP;
				stepCnt++;
				if (ay + myAgentLime.getHeight() < 0) {
					ay = GameProperties.SCREEN_HEIGHT;
					dimensionCnt++;
					if (dimensionCnt==3) {score+=5000;}
				}
				if (myBox.getVisible() && myAgentLime.r.intersects(myBox.getRectangle())) {
					by -= GameProperties.CHARACTER_STEP;
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
			if (myAgentLime.getCanMove()) {
				ay += GameProperties.CHARACTER_STEP;
				stepCnt++;
				if (ay > GameProperties.SCREEN_HEIGHT) {
					ay = -1 * myAgentLime.getHeight();
					dimensionCnt++;
					if (dimensionCnt==3) {score+=5000;}
				}
				if (myBox.getVisible() && myAgentLime.r.intersects(myBox.getRectangle())) {
					by += GameProperties.CHARACTER_STEP;
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			for (int i = 0; i < WallLabel.length; i++) {
				if (ax + myAgentLime.getWidth() - GameProperties.CHARACTER_STEP > WallLabel[i].getX() 
						&& ax - GameProperties.CHARACTER_STEP < WallLabel[i].getX() + WallLabel[i].getWidth() 
						&& ay + myAgentLime.getHeight() > WallLabel[i].getY() 
						&& ay < WallLabel[i].getY() + WallLabel[i].getHeight() 
						|| ax - GameProperties.CHARACTER_STEP < 0) {
					myAgentLime.setCanMove(false);
				}
			} if (myAgentLime.getCanMove()) {
				ax -= GameProperties.CHARACTER_STEP;
				stepCnt++;
				if (myBox.getVisible() && myAgentLime.r.intersects(myBox.getRectangle())) {
					bx -= GameProperties.CHARACTER_STEP;
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			for (int i = 0; i < WallLabel.length; i++) {
				if (ax + myAgentLime.getWidth() + GameProperties.CHARACTER_STEP > WallLabel[i].getX() 
						&& ax + GameProperties.CHARACTER_STEP < WallLabel[i].getX() + WallLabel[i].getWidth() 
						&& ay + myAgentLime.getHeight() > WallLabel[i].getY() 
						&& ay < WallLabel[i].getY() + WallLabel[i].getHeight() 
						|| ax + (myAgentLime.getWidth()*2) + GameProperties.CHARACTER_STEP >= GameProperties.SCREEN_WIDTH) {
					myAgentLime.setCanMove(false);
				}
			} if (myAgentLime.getCanMove()) {
				ax += GameProperties.CHARACTER_STEP;
				stepCnt++;
				if (myBox.getVisible() && myAgentLime.r.intersects(myBox.getRectangle())) {
					bx += GameProperties.CHARACTER_STEP;
				}
			}
		} else if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			if (myAgentLime.r.intersects(myStopBtn.getRectangle())) {
				if (!myStopBtn.getIsOn()) {
					myStopBtn.setIsOn(true);
					myStopBtn.setWasOn(true);
					for (int i = 0; i < myLavaWall.length; i++) {
						myLavaWall[i].setMoving(false);
					}
				} else {
					myStopBtn.setIsOn(false);
					for (int i = 0; i < myLavaWall.length; i++) {
						myLavaWall[i].setMoving(true);
					}
				}
			} else if (myAgentLime.r.intersects(myFinishBtn.getRectangle())) {
				myFinish.setX(700);
				myFinish.setY(50);
				myFinish.setVisible(true);
				FinishLabel.setLocation(myFinish.getX(), myFinish.getY());
			}
		}
		
		myAgentLime.setX(ax);
		myAgentLime.setY(ay);
		
		AgentLimeLabel.setLocation(myAgentLime.getX(), myAgentLime.getY());
		
		myBox.setX(bx);
		myBox.setY(by);
		
		BoxLabel.setLocation(myBox.getX(), myBox.getY());
		
		if (myBox.getVisible() && myBox.r.intersects(myGoal.getRectangle())) {
			myBox.setVisible(false); // don't know why but my visibility flag doesn't actually show/hide so I have to put it in an unseen spot
			myBox.setX(9999);
			myBox.setY(9999);
			GoalLabel.setIcon(new ImageIcon(getClass().getResource("goalFull.png")));
			score+=25000; // bonus for box puzzle
		}
		
		if (myFinish.getVisible() && myAgentLime.r.intersects(myFinish.getRectangle())) {
			score+=(50000+(timeLeft*1000));
			if (!myStopBtn.getWasOn()) {score+=5000;} // bonus if player never stopped moving obstacles
			if (stepCnt<250) {score+=10000;} // stealth bonus
			won = true;
		}
		
		myAgentLime.setCanMove(true);
	}

	@Override
	public void keyReleased(KeyEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	public void gameOver(String name,int score,boolean isAlive,boolean isTimeOut) {
		popup = new PopUpMessage(name,score,isAlive,isTimeOut);
		popup.displayGUI();
		database = new GameSQLite(name,score);
		database.Interact(name,score);
		System.exit(0);
	}
}
