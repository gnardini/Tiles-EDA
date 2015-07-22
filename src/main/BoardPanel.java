package main;

import game.PlayMaker;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

public class BoardPanel extends JLayeredPane implements ActionListener{
	
	private static final long serialVersionUID = 1L;
	
	private PlayMaker pm;
	private Cursor humanTurn, pcTurn;
	private JLabel gameOver;
	private JButton close;
	
	public BoardPanel(PlayMaker pm, Color[] colors, int tileSIZE, int sizeX, int sizeY){
		setLayout(null);
		setSize(tileSIZE*sizeX,tileSIZE*sizeY);
		humanTurn=Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
		pcTurn=Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR);
		/*
		BufferedImage cursorImg = new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB);
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor( cursorImg, new Point(16, 16), "blank cursor");
		pcTurn=blankCursor;
		*/
		setCursor(humanTurn);
		this.pm=pm;
		
		pm.setBoardPanel(this);
		
		gameOver = new JLabel("");
		gameOver.setFont(gameOver.getFont().deriveFont((float)30));
		gameOver.setBounds(getWidth()/2-100,getHeight()/2-20, 400,40);
		add(gameOver,new Integer(2));
		close=new JButton("Close Game");
		close.setBounds(getWidth()/2-100,getHeight()/2+50, 200,40);
		close.addActionListener(this);
		close.setVisible(false);
		add(close,new Integer(2));
		
		for(int i=0 ; i<sizeX;i++){
			for(int j=0;j<sizeY;j++){
				TilePanel tile=new TilePanel(pm,tileSIZE,i,j,colors);
				tile.setBounds(i*tileSIZE,(sizeY-1-j)*tileSIZE,tileSIZE,tileSIZE);
				add(tile,new Integer(1));
			}
		}
		
	}
	
	public void moveMade(){
		for(Component c: getComponents())
			c.repaint();
		
		if(pm.getBoard().isPCturn()) setCursor(pcTurn);
		else setCursor(humanTurn);
	}
	
	public void gameOver(){
		int winner=pm.getBoard().getPoints(0)-pm.getBoard().getPoints(1);
		gameOver.setText(winner==0 ? "Draw" : ("Player " + (winner>0 ? 1:2) + " wins!"));
		close.setVisible(true);
	}

	
	public void actionPerformed(ActionEvent ae) {
		System.exit(0);
	}
	
}
