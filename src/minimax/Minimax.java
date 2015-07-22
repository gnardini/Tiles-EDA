package minimax;

import game.Board;
import game.PlayMaker;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.LinkedList;
import java.util.Queue;

import javax.swing.Timer;

import main.GameCenter;

public class Minimax implements Runnable{

	private PlayMaker pm;
	private BoardState root;
	private int maxLevel, maxTime;
	private boolean prune, tree;
	private volatile boolean time;
	
	public Minimax(PlayMaker pm, Board board, int maxLevel, int maxTime, boolean prune, boolean tree){
		root=new StateMax(board.getCopy(), new Point(-1,-1));
		this.pm=pm;
		this.maxLevel=maxLevel;
		this.maxTime=maxTime;
		this.prune=prune;
		this.tree=tree;
		time=true;
		Thread t=new Thread(this,"Minimax");
		t.start();
	}
	
	public void run() {
		if(maxTime!= GameCenter.INFINITE){
			runTimeAlgorithm();
		}else{
			runAlgorithm(root, maxLevel,GameCenter.INFINITE);
		}
		returnMove();
	}
	
	private void runTimeAlgorithm(){
		Queue<BoardState> q=new LinkedList<BoardState>();
		q.add(root);
		
		Timer timer=new Timer(maxTime*900,new ActionListener(){
			public void actionPerformed(ActionEvent ae) {
				timeUp();
			}
		});
		timer.setRepeats(false);
		timer.start();
		int parentScore=root.getScore();
		while(time && !q.isEmpty()){
			BoardState state=q.remove();
			BoardState chosen=null;
			for(BoardState child: state.getBoard().possibleMoves(state.isMax())){
				state.addChild(child);
				if(!prune || state.analizeState(parentScore)){
					q.add(child);
					int childScore=child.calculateScore();
					if(state.updateScore(childScore)){
						if(chosen!=null) chosen.chosen(false);
						chosen=child;
						chosen.chosen(true);
					}
				}else{
					child.pruned();
				}
				
			}
		}
	}
	
	private int runAlgorithm(BoardState state, int deep, int parentScore){
		if(deep==0 || !time){
			state.calculateScore();
			return state.getScore();
		}
		
		BoardState chosen=null;
		for(BoardState child: state.getBoard().possibleMoves(state.isMax())){
			state.addChild(child);
			if(!prune || state.analizeState(parentScore)){
				int childScore=runAlgorithm(child, deep-1, state.getScore());
				if(state.updateScore(childScore)){
					if(chosen!=null) chosen.chosen(false);
					chosen=child;
					chosen.chosen(true);
				}
			}else{
				child.pruned();
			}
			
		}
		return state.getScore();
	}
	
	private void returnMove(){
		Board optimal=root.getOptimalState().getBoard();
		if(tree) drawTree();
		pm.recievePCmovement(optimal);
	}
	
	private void drawTree(){
		BufferedWriter writer = null;

		try {
		    writer = new BufferedWriter(new OutputStreamWriter( new FileOutputStream("tree.dot"), "utf-8"));
		    writer.write("digraph Tree{");
		    writer.newLine();
		    writer.write(root.hashCode() + " [label=\"START " + root.getScore() +"\"" + " style=filled  fillcolor=brown" +"]");
			writer.newLine();
		    root.drawTree(writer);
		    writer.write("}");
		} catch (IOException e) {}
		finally {
		   try {writer.close();} 
		   catch (Exception e) {}
		}
	}
	
	public void timeUp(){
		time=false;
	}
}
