package minimax;

import game.Board;

import java.awt.Point;
import java.io.BufferedWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import main.GameCenter;

public abstract class BoardState {

	private Board board;
	private List<BoardState> childs;
	private int score;
	private Point move;
	private boolean pruned, chosen;
	
	public BoardState(Board board, Point move, int score){
		this.board=board;
		this.score=score;
		this.move=move;
		childs=new LinkedList<BoardState>();
	}
	
	public void addChild(BoardState state){
		childs.add(state);
	}
	
	public BoardState getOptimalState(){
		if(childs.size()==0){
			return this;
		}
		int max=-GameCenter.INFINITE-1;
		BoardState optimal=null;
		for(BoardState child: childs){
			int childScore=child.getScore();
			if(childScore>max){
				max=childScore;
				optimal=child;
			}
		}
		score=max;
		return optimal;
	}
	
	public int getScore(){
		return score;
	}
	
	public void setScore(int score){
		this.score=score;
	}
	
	public abstract boolean updateScore(int childScore);
	public abstract boolean analizeState(int parentScore);
	
	public String getLabel(){
		String scoreString;
		if(pruned) scoreString="";
		else if(score==GameCenter.INFINITE) scoreString="WIN";
		else if(score==-GameCenter.INFINITE) scoreString="LOSE";
		else scoreString=score+"";
		return "\"("+move.x+","+move.y+")"+" "+scoreString+"\"";
	}
	
	public void drawTree(BufferedWriter writer) throws IOException{
		for(BoardState child:childs){
			writer.write(child.hashCode() + " [label=" + child.getLabel());
			if(!child.isMax()) writer.write(" shape=box ");
			if(child.pruned || child.chosen) writer.write(" style=filled ");
			if(child.chosen) writer.write(" fillcolor=brown ");
			writer.write("]");
			writer.newLine();
			writer.write(hashCode()+"->"+child.hashCode());
			writer.newLine();
		}
		for(BoardState child:childs){
			child.drawTree(writer);
		}
	}
	
	public Board getBoard(){
		return board;
	}
	
	public void pruned(){
		pruned=true;
	}
	
	public void chosen(boolean chosen){
		this.chosen=chosen;
	}
	
	public abstract boolean isMax();
	
	public int calculateScore(){
		score=board.getScore();
		return score;
	}
}
