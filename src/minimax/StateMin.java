package minimax;

import game.Board;

import java.awt.Point;

import main.GameCenter;

public class StateMin extends BoardState{

	public StateMin(Board board, Point move){
		super(board,move,GameCenter.INFINITE);
	}
	
	@Override
	public boolean analizeState(int parentScore) {
		return getScore()>parentScore;
	}
	
	@Override
	public boolean updateScore(int childScore) {
		int score=getScore();
		setScore(Math.min(getScore(), childScore));
		return score!=getScore();
	}
	
	@Override
	public boolean isMax(){
		return false;
	}
}
