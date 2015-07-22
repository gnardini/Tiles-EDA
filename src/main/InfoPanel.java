package main;

import game.Board;
import game.PlayMaker;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

public class InfoPanel extends JPanel{
	
	private static final long serialVersionUID = 1L;
	private PlayMaker pm;
	private int WIDTH;
	
	public InfoPanel(PlayMaker pm, int WIDTH) {
		setLayout(null);
		setSize(WIDTH,40);
		this.pm=pm;
		this.WIDTH=WIDTH;
		
		pm.setInfoPanel(this);
		
		setVisible(true);
	}
	
	public void moveMade(){
		repaint();
	}

	public void paint(Graphics g){
		Board board=pm.getBoard();
		
		g.setColor(Color.WHITE);
		g.fillRect(0, 0, WIDTH, 40);
		g.setColor(Color.BLACK);
		g.fillRect(0, 37, WIDTH, 3);
		g.setColor(Color.BLACK);
		g.setFont(g.getFont().deriveFont((float)15));
		g.drawString("Player 1: " + board.getPoints(0), WIDTH/4, 20);
		g.drawString("Player 2: " + board.getPoints(1), WIDTH/4*3, 20);
		if(board.isPCturn()){
			g.setFont(g.getFont().deriveFont((float)12));
			g.drawString("Thinking...", WIDTH*3/4, 34);
		}
	}
	
}
