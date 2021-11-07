package def;

import java.awt.EventQueue;
import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class PopUpMessage {
	protected int score;
	protected boolean isAlive, isTimeOut;
	
    public int getScore() {
		return score;
	}
	public void setScore(int score) {
		this.score = score;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setAlive(boolean isAlive) {
		this.isAlive = isAlive;
	}
	public boolean isTimeOut() {
		return isTimeOut;
	}
	public void setTimeOut(boolean isTimeOut) {
		this.isTimeOut = isTimeOut;
	}

	public PopUpMessage() {
		this.score = 0;
		this.isAlive = false;
		this.isTimeOut = false;
	}
	
	public PopUpMessage(int score, boolean isAlive, boolean isTimeOut) {
		super();
		this.score = score;
		this.isAlive = isAlive;
		this.isTimeOut = isTimeOut;
	}

    public void displayGUI() {
        JOptionPane.showMessageDialog(
            null, getPanel(), "Results",
                JOptionPane.INFORMATION_MESSAGE);
    }

    private JPanel getPanel() {
        JPanel panel = new JPanel(new GridLayout(4,1,0,0));
        JLabel label1 = getLabel("test");
        JLabel label2 = getLabel("");
        JLabel label3 = getLabel("test");
        JLabel label4 = getLabel("test");
        
        if (this.isAlive()) {
        	label1.setText("Mission Complete: Agent Lime fininshed the infiltration");
        } else if (!this.isAlive()) {
        	label1.setText("Mission Failed: Agent Lime K.I.A.");
        } else if (this.isTimeOut()) {
        	label1.setText("Mission Terminated: Time limit exceeded.");
        }
        
        label3.setText("Score: "+this.getScore());
        label4.setText("Player data written to database...");
        
        panel.add(label1);
        panel.add(label2);
        panel.add(label3);
        panel.add(label4);

        return panel;
    }

    private JLabel getLabel(String title) {
        return new JLabel(title);
    }

    public static void main(String[] args) {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                new PopUpMessage().displayGUI();
            }
        };
        EventQueue.invokeLater(runnable);
    }
}