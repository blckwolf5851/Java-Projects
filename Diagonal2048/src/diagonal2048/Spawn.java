package diagonal2048;
/***********
 * A main program spawn new node at avaliable index
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */

import java.util.ArrayList;
import java.util.Random;

public class Spawn {
	/*
	public static void main(String[] arg) {
		int[][] grid = {{0,0,0,0},{0,0,0,0}};
		int[][] mergedgrid = spawn(grid);
		for(int i = 0; i < mergedgrid.length; i++) {
			for(int j = 0; j < mergedgrid[i].length; j++) {
				System.out.print(mergedgrid[i][j]+" ");
			}
			System.out.println();
		}
		
	}
	*/
	
	/**
	 * spawn new node to a zero-index
	 * @param int[][], 2D array to be modified
	 * @return int[][], resulting 2D array
	 */
	public static int[][] spawn(int[][] grid){
		Random rand = new Random();
		ArrayList<Integer> nonZeroRow = new ArrayList<Integer>();
		ArrayList<Integer> nonZeroCol = new ArrayList<Integer>();
		int[] node = {-1,-1};//[row,column]
		int chose = (int)Math.round(Math.random()); //0 or 1
		for(int row = 0; row < grid.length; row++) {
			for (int column = 0; column < grid[row].length; column++) {
				chose = (int)(Math.round(Math.random()));
				if(grid[row][column] == 0 && chose == 1) { //chose to use the index by random chance
					node[0] = row;
					node[1] = column;
					break;
				}
			}
		}
		
		if(node[0] != -1 && node[1] != -1) {
			int[] option = {2,2,2,4,4,8};
			//chance of getting 2 is higher than 4 and 8, 4 is higher thatn 8
			grid[node[0]][node[1]] = option[(int) (Math.floor(Math.random()*3))];
		}
				
		return grid;
	}
}
