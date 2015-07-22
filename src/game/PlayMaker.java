package game;

import main.BoardPanel;
import main.InfoPanel;
import minimax.Minimax;

public class PlayMaker {

	private Board board;
	private BoardPanel bp;
	private InfoPanel ip;
	private int maxTime, maxLevel;
	private boolean prune, tree;
	private long start;
	
	public PlayMaker(Board board, int maxLevel, int maxTime, boolean prune, boolean tree){
		this.board=board;
		this.maxLevel=maxLevel;
		this.maxTime=maxTime;
		this.prune=prune;
		this.tree=tree;
	}
	
	public void makeMove(int x, int y){
		if(board.isPCturn()) return;
		boolean moved=board.touched(0, x, y, null);
		if(moved){
			moveMade();
			new Minimax(this,board,maxLevel,maxTime,prune,tree);
			start=System.currentTimeMillis();
		}
	}
	
	public void recievePCmovement(Board b){
		int elapsed=(int)(System.currentTimeMillis()-start);
		if(elapsed<1000){
			try{Thread.sleep(1000-elapsed);}catch(Exception e){}
		}
		System.out.println(elapsed);
		board=b;
		board.PCfinished();
		moveMade();
	}
	
	private void moveMade(){
		bp.moveMade();
		ip.moveMade();
		if(board.gameOver()){
			bp.gameOver();
		}
	}
	
	public void setBoardPanel(BoardPanel bp){
		this.bp=bp;
	}
	
	public void setInfoPanel(InfoPanel ip){
		this.ip=ip;
	}
	
	public Board getBoard(){
		return board;
	}
}
