package game;

import java.awt.Point;
import java.util.LinkedList;
import java.util.List;

import main.GameCenter;
import minimax.BoardState;
import minimax.StateMax;
import minimax.StateMin;

public class Board {

	private Tile[][] board;
	private int[] points;
	private int sizeX, sizeY;
	private boolean pcTurn;
	
	
	public Board(Tile[][] board, int pointsp1, int pointsp2){
		this.board=board;
		sizeX=board.length;
		sizeY=board[0].length;
		points=new int[2];
		points[0]=pointsp1;
		points[1]=pointsp2;
	}
	
	
	public boolean touched(int player, int x, int y, Tile[][] old){
		if(!validMove(board[x][y].getColor(),x,y)) return false;
		int n=eliminate(board[x][y].getColor(),x,y, old);
		n=calculatePoints(n);
		points[player]+=n;
		gravity();
		moveLeft();
		pcTurn=!pcTurn;
		if(gameOver()) points[player]*=1.3;
		return true;
	}
	
	public int eliminate(int color,int x, int y, Tile[][] old){
		if(x<0 || x>=sizeX || y<0 || y>=sizeY || board[x][y].getColor()!=color) return 0;
		if(old!=null) old[x][y].mark();
		
		board[x][y].eliminate();
		return 1 +eliminate(color,x-1,y,old) + eliminate(color,x+1,y,old) + eliminate(color,x,y+1,old) + eliminate(color,x,y-1,old);
	}
	
	private boolean validMove(int color, int x, int y){
		if(board[x][y].getColor()==0) return false;
		if(x>0 && board[x-1][y].getColor()==color) return true;
		if(x<sizeX-1 && board[x+1][y].getColor()==color) return true;
		if(y>0 && board[x][y-1].getColor()==color) return true;
		if(y<sizeY-1 && board[x][y+1].getColor()==color) return true;
		return false;
	}
	
	private void gravity(){
		for(int i=0 ; i<sizeX ; i++){
			for(int j=0 ; j<sizeY-1;j++){
				if(board[i][j].getColor()==0){
					int k=j+1;
					boolean flag=true;
					while(k<sizeY && flag){
						if(board[i][k].getColor()!=0){
							board[i][j]=board[i][k];
							board[i][k]=new Tile(0);
							flag=false;
							k--;
						}
						k++;
					}
					if(k>=sizeY) continue;
				}
			}
		}
	}
	
	private int calculatePoints(int n){
		if(n<=3) n--;
		else if(n==5) n=8;
		else if(n>5) n*=2;
		return n;
	}
	
	private void moveLeft(){
		for(int i=0 ; i<sizeX-1 ; i++){
			if(board[i][0].getColor()==0){
				boolean flag=true;
				int k=i+1;
				while(k<sizeX && flag){
					if(board[k][0].getColor()!=0){
						Tile[] aux=board[i];
						board[i]=board[k];
						board[k]=aux;
						flag=false;
						k--;
					}
					k++;
				}
				if(k>=sizeX) continue;
			}
		}
	}
	
	public boolean gameOver(){
		if(board[0][0].getColor()==0) return true;
		for(int i=0 ; i<sizeX ; i++){
			for(int j=0 ; j<sizeY ; j++){
				if(validMove(board[i][j].getColor(),i,j))
					return false;
			}
		}
		return true;
	}
	
	public int getPoints(int n){
		if(n>1) return -1;
		return points[n];
	}
	
	public Tile[][] getBoard(){
		return board;
	}
	
	public int getScore(){
		int ans=points[1]-points[0];
		if(!gameOver() || ans==0) return ans;
		if(ans>0) return GameCenter.INFINITE;
		else  return -GameCenter.INFINITE;
	}
	
	public List<BoardState> possibleMoves(boolean max){
		List<BoardState> moves = new LinkedList<BoardState>();
		
		for(int i=0;i<sizeX;i++){
			for(int j=0;j<sizeY;j++){
				if(board[i][j].getColor()==0) continue;
				if(!board[i][j].isMarked() && validMove(board[i][j].getColor(), i, j)){
					Board move=new Board(getBoardCopy(),points[0],points[1]);
					move.touched(max ? 1:0, i, j,board);
					BoardState child;
					if(max) child=new StateMin(move, new Point(j,i));
					else child=new StateMax(move, new Point(j,i));
					moves.add(child);
				}
			}
		}
		
		return moves;
	}
	
	private Tile[][] getBoardCopy(){
		Tile[][] boardCopy=new Tile[sizeX][sizeY];
		
		for(int i=0 ;i<sizeX;i++){
			for(int j=0 ; j<sizeY; j++){
				boardCopy[i][j]=new Tile(board[i][j].getColor());
			}
		}
		
		return boardCopy;
	}
	
	public Board getCopy(){
		return new Board(getBoardCopy(),points[0],points[1]);
	}
	
	public int getWidth(){
		return sizeX;
	}
	
	public int getHeight(){
		return sizeY;
	}
	
	public boolean isPCturn(){
		return pcTurn;
	}
	
	public void PCfinished(){
		pcTurn=false;
	}
	
	
	public void createRandomBoard(int x, int y, int colors){
		sizeX=x;
		sizeY=y;
		
		board=new Tile[sizeX][sizeY];
		for(int i=0 ; i<sizeX ; i++){
			for(int j=0 ; j<sizeY ; j++){
				board[i][j]=new Tile((int)(Math.random()*colors)+1);
			}
		}
	}
}
