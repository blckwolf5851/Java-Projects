
package diagonal2048;
/***********
 * A  program that check if the game is over or win both diagonally and straight
 * 
 * @author Sherry Yuan
 * @version 1.0 (2018/6/2)
 * ****************
 */
public class GameOver {
	/*
	public static void main(String[] arg) {
		int[][] grid = {{2,4},{2,8}};
		int mergedgrid = gameOver(grid);
		
		System.out.println(mergedgrid);
		
		
	}
	*/
	/**
	 * Check if game over or win, straight check
	 * @param int[][], 2D array to be modified
	 * @return int 0,1,2, 0 = game not over, 1 = game loss, 2 = game win
	 */
	public static int gameOver(int[][] grid) {
		// win = 2; not end = 0; lose = 1;
		for (int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[0].length; col ++) {
				//if game win
				if(grid[row][col]== 2048) {
					return 2;
				}
				if(grid[row][col]==0) {
					return 0;
				}
				if(row > 0 && col > 0) {
					if(grid[row][col] == grid[row][col-1] || grid[row][col] == grid[row-1][col]) {
						return 0;
					}
				}
				if(row < grid.length-1 && col < grid[0].length-1) {
					if(grid[row][col] == grid[row][col+1] || grid[row][col] == grid[row+1][col]) {
						return 0;
					}
				}
			
			}
		}
		return 1;
		
	}
	/**
	 * Check if game over diagonally
	 * @param int[][], 2D array to be modified
	 * @return boolean, true = game lose, false = game not end
	 */
public static boolean diagGameover(int[][] grid) {
		
		for (int row = 0; row < grid.length; row++) {
			for(int col = 0; col < grid[0].length; col ++) {
				if(grid[row][col]==0) {
					return false;
				}
				if(row > 0 && col > 0) {
					if(grid[row][col] == grid[row-1][col-1]) {
						return false;
					}
				}
				if(row>0 && col < grid[0].length-1) {
					if(grid[row][col] == grid[row-1][col+1]) {
						return false;
					}
				}
				if(row < grid.length-1 && col > 0) {
					if(grid[row][col] == grid[row+1][col-1]) {
						return false;
					}
				}
				if(row < grid.length-1 && col < grid[0].length-1) {
					if(grid[row][col] == grid[row+1][col+1]) {
						return false;
					}
				}
			
			}
		}
		return true;
		
	}
}
