package minimax;

import game.Board;

import java.awt.Point;

import main.GameCenter;

public class StateMax extends BoardState {

	public StateMax(Board board, Point move){
		super(board,move,-GameCenter.INFINITE);
	}
	
	@Override
	public boolean analizeState(int parentScore) {
		return getScore()<parentScore;
	}
	
	@Override
	public boolean updateScore(int childScore) {
		int score=getScore();
		setScore(Math.max(getScore(), childScore));
		return score!=getScore();
	}
	
	public boolean isMax(){
		return true;
	}
	
}
