package game;

public class Tile {

	private int color;
	private boolean mark;
	
	public Tile(int color){
		this.color=color;
	}
	
	public void eliminate(){
		color=0;
	}
	
	public int getColor(){
		return color;
	}
	
	public void mark(){
		mark=true;
	}
	
	public boolean isMarked(){
		return mark;
	}
}
