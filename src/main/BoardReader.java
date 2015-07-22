package main;

import game.Board;
import game.Tile;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class BoardReader {

	public static Board startGame(String name){
		BufferedReader br=null;
		Board board=null;
		try{
			br = new BufferedReader(new FileReader(name));
			int sizeY=Integer.parseInt(br.readLine());
			int sizeX=Integer.parseInt(br.readLine());
			int p1points=Integer.parseInt(br.readLine());
			int p2points=Integer.parseInt(br.readLine());
			
			Tile[][] b=new Tile[sizeX][sizeY];
			
			for(int j=sizeY-1 ; j>=0 ; j--){
				for(int i=0 ; i<sizeX; i++){
					int c;
					switch(c=br.read()){
					case ' ':b[i][j]=new Tile(0);break;
					default: b[i][j]=new Tile(c-'0');
					}
				}
				br.read();br.read();
			}
			
			board=new Board(b,p1points,p2points);
			
			
		}catch(FileNotFoundException e){
			System.out.println("No existe el tablero.");
		}catch(IOException e){
			System.out.println("Los datos en el tablero son incorrectos y/o el tablero esta incompleto");
		}finally{
			try{
				if(br!=null) br.close();
			}catch(IOException e){}
			
		}
		
		return board;
	}
}
