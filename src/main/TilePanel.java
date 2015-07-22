package main;

import game.PlayMaker;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JPanel;

public class TilePanel extends JPanel{

	private static final long serialVersionUID = 1L;
	private PlayMaker pm;
	private int x,y;
	private boolean selected;
	private Color[] colors;
	
	public TilePanel(PlayMaker pm, int tileSIZE, int x, int y, Color[] colors){
		setLayout(null);
		setSize(tileSIZE,tileSIZE);
		addMouseListener(new myMouseListener());
		
		this.pm=pm;
		this.x=x;
		this.y=y;
		this.colors=colors;
	}
	
	public void paint(Graphics g){
		g.setColor(colors[pm.getBoard().getBoard()[x][y].getColor()]);
		g.fillRect(0, 0, getWidth(), getHeight());
		if(selected && !pm.getBoard().isPCturn()){
			g.setColor(Color.YELLOW);
			g.fillRect(0, 0, getWidth(),getHeight()/10 );
			g.fillRect(0, 0, getWidth()/10,getHeight() );
			g.fillRect(0, (int)(getHeight()*0.9), getWidth(),getHeight()/10 );
			g.fillRect((int)(getWidth()*0.9), 0, getWidth()/10,getHeight() );
		}
	}
	
	private class myMouseListener extends MouseAdapter{
		
		@Override
		public void mouseClicked(MouseEvent me) {
			pm.makeMove(x, y);
		}
		
		@Override
		public void mouseEntered(MouseEvent arg0) {
			selected=true;
			repaint();
		}
		
		@Override
		public void mouseExited(MouseEvent arg0) {
			selected=false;
			repaint();
		}
		
	}
}
